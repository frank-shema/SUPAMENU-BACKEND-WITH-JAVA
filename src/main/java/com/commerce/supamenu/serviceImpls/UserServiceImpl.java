package com.commerce.supamenu.serviceImpls;

import com.commerce.supamenu.dto.requests.registration.RegisterRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.user.UserResponse;
import com.commerce.supamenu.enums.ERole;
import com.commerce.supamenu.exceptions.BadRequestException;
import com.commerce.supamenu.helpers.Converters;
import com.commerce.supamenu.models.Role;
import com.commerce.supamenu.models.User;
import com.commerce.supamenu.repositories.IRoleRepository;
import com.commerce.supamenu.repositories.IUserRepository;
import com.commerce.supamenu.services.IUserService;
import com.commerce.supamenu.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public ApiResponse<UserResponse> register(RegisterRequest req) {
        if(userRepository.existsByEmail(req.getEmail())) throw new BadRequestException("Email already in use");
        Role role = null;
        if(req.getRole() != null){
            // get the role
            role = roleRepository.findByRole(req.getRole()).orElseThrow(() -> new BadRequestException("Role not found"));
        }else{
            role = roleRepository.findByRole(ERole.ROLE_CUSTOMER).orElseThrow(() -> new BadRequestException("Role not found"));
        }

        // create the user
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPassword(Utility.hash(req.getPassword()));
        user.setRole(role);
        user.setPhoneNumber(req.getPhoneNumber());
        user.setOrders(new ArrayList<>());

        // save the user
        userRepository.save(user);
        UserResponse userResponse = Converters.convertToUserResponse(user);
        return ApiResponse.success(userResponse);
    }
}
