package com.example.authservice.controllers.impl;

import com.example.authservice.controllers.AuthController;
import com.example.authservice.exceptions.BadRequestException;
import com.example.authservice.models.ResponseAuthenticationModel;
import com.example.authservice.models.ResponseAuthorizationModel;
import com.example.authservice.services.BruteForceProtectService;
import com.example.authservice.services.JwtService;
import com.example.authservice.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private final BruteForceProtectService protectService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseAuthenticationModel login(String username, String password) {
        if (protectService.isBlocked(username)) {
            throw new BadRequestException("User is blocked for 5 min");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());
        String jwt = jwtService.generateToken(userDetails);

        return new ResponseAuthenticationModel(username, jwt);
    }

    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        String username = jwtService.extractUsername(token);
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return new ResponseAuthorizationModel(username, authorities);
    }
}
