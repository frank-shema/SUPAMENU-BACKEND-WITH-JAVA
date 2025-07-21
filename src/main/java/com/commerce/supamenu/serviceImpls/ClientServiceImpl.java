package com.commerce.supamenu.serviceImpls;

import com.commerce.supamenu.dto.requests.client.ClientRequest;
import com.commerce.supamenu.dto.requests.restaurant.RestaurantRequest;
import com.commerce.supamenu.dto.requests.user.UserUpdateRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.client.ClientResponse;
import com.commerce.supamenu.dto.responses.client.ClientSummaryResponse;
import com.commerce.supamenu.enums.EClientStatus;
import com.commerce.supamenu.enums.ERole;
import com.commerce.supamenu.exceptions.NotFoundException;
import com.commerce.supamenu.helpers.Converters;
import com.commerce.supamenu.models.*;
import com.commerce.supamenu.repositories.*;
import com.commerce.supamenu.services.IClientService;
import com.commerce.supamenu.services.IMailService;
import com.commerce.supamenu.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final IClientRepository clientRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IMailService mailService;
    private final IActivationCodeRepository activationCodeRepository;
    private final IPhotoRepository photoRepository;
    private final IRestaurantRepository restaurantRepository;

    private static final int ACTIVATION_CODE_EXPIRY_HOURS = 24;


    @Override
    @Transactional
    public ApiResponse<ClientSummaryResponse> createClient(ClientRequest request) {
        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setStatus(EClientStatus.PENDING_SETUP);
        client.setRepresentative(request.getRepresentative());
        client.setBankAccount(request.getBankAccount());
        client.setRestaurants(new HashSet<>());
        client.setEmail(request.getEmail());

        // create the user
        User user = new User();
        Role role = roleRepository.findByRole(ERole.ROLE_CLIENT).orElseThrow(() -> new NotFoundException("The role not found."));
        user.setRole(role);
        user.setEnabled(false);
        user.setEmail(request.getEmail());
        userRepository.save(user);
        Client nc = clientRepository.save(client);

        String activationToken = UUID.randomUUID().toString();
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(activationToken);
        activationCode.setExpiryDate(LocalDateTime.now().plusHours(ACTIVATION_CODE_EXPIRY_HOURS));
        activationCode.setUser(user);
        activationCodeRepository.save(activationCode);

        mailService.sendClientActivationEmail(request.getEmail(), nc.getId(), activationToken);

        return ApiResponse.success(
                "Client created successfully. Activation email sent.",
                Converters.convertToClientSummaryResponse(nc)
        );
    }

    @Override
    public ApiResponse<ClientResponse> getClient(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + clientId));
        return ApiResponse.success(Converters.convertToClientResponse(client));
    }

    @Override
    @Transactional
    public ApiResponse<String> activateClient(UUID clientId, String activationCode) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        User user = userRepository.findByEmail(client.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));

        ActivationCode code = activationCodeRepository.findByCodeAndUser(activationCode, user)
                .orElseThrow(() -> new NotFoundException("Invalid activation code"));

        if (code.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Activation code has expired");
        }

        // Activate the user account
        user.setEnabled(true);
        userRepository.save(user);

        // Update client status
        client.setStatus(EClientStatus.COMPLETING_PROFILE);
        clientRepository.save(client);

        // Delete used activation code
        activationCodeRepository.delete(code);

        // Send welcome email
        mailService.sendWelcomeEmail(user.getEmail(), client.getClientName());

        return ApiResponse.success("Client activated successfully");
    }

    @Override
    @Transactional
    public ApiResponse<String> completeClientSetup(UUID clientId, String password,
                                                   UserUpdateRequest userUpdate,
                                                   RestaurantRequest restaurantRequest) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        if (!client.getStatus().equals(EClientStatus.COMPLETING_PROFILE)) {
            throw new IllegalStateException("Client must have COMPLETING PROFILE Status.");
        }

        // Step 1: Update User Info
        User user = userRepository.findByEmail(client.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setPhoneNumber(userUpdate.getPhoneNumber());
        user.setPassword(Utility.hash(password)); // Set password
        userRepository.save(user);

        // Step 2: Create Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setCategory(restaurantRequest.getCategory());
        restaurant.setAddress(restaurantRequest.getAddress());
        restaurant.setRestoName(restaurantRequest.getRestoName());
        restaurant.setRestoFullName(restaurantRequest.getRestoFullName());
        restaurant.setStartTime(restaurantRequest.getStartTime());
        restaurant.setEndTime(restaurantRequest.getEndTime());
        restaurant.setClient(client); // 🔗 Link to client

        restaurantRepository.save(restaurant);

        // Step 3: Save restaurant photos if any
        if (restaurantRequest.getPhotos() != null && !restaurantRequest.getPhotos().isEmpty()) {
            List<Photo> photos = restaurantRequest.getPhotos().stream()
                    .map(photoRequest -> {
                        Photo photo = new Photo();
                        photo.setUrl(photoRequest.getUrl());
                        return photo;
                    }).collect(Collectors.toList());

            photoRepository.saveAll(photos);
        }

        // Step 4: Activate client
        client.setStatus(EClientStatus.COMPLETED);
        clientRepository.save(client);

        return ApiResponse.success("Client setup completed successfully 🚀🔥");
    }

}
