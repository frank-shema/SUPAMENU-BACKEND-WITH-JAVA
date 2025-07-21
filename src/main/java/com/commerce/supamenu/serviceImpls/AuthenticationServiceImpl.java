package com.commerce.supamenu.serviceImpls;

import com.commerce.supamenu.dto.requests.auth.ChangePasswordRequest;
import com.commerce.supamenu.dto.requests.auth.LoginRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.auth.AuthResponse;
import com.commerce.supamenu.exceptions.BadCredentialsException;
import com.commerce.supamenu.exceptions.UserNotFoundException;
import com.commerce.supamenu.models.User;
import com.commerce.supamenu.repositories.IUserRepository;
import com.commerce.supamenu.security.jwt.JwtService;
import com.commerce.supamenu.services.IAuthenticationService;
import com.commerce.supamenu.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<AuthResponse> authenticate(LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid login credentials."));

        String token = jwtService.generateToken(user);
        return ApiResponse.success(new AuthResponse(token));
    }

    @Override
    public ApiResponse<String> changePassword(ChangePasswordRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!Utility.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        if (Utility.matches(req.getNewPassword(), user.getPassword())) {
            throw new BadCredentialsException("New password must be different from current password");
        }

        user.setPassword(Utility.hash(req.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.success("Password changed successfully");
    }
}
