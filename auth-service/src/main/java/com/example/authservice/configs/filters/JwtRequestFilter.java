package com.example.authservice.configs.filters;

import com.example.authservice.services.JwtService;
import com.example.authservice.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            putUserToContext(request);
        } catch (Exception ex) {
            log.warn("Something went wrong e {}", ex.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void putUserToContext(HttpServletRequest request) {
        Optional.of(request)
                .map(this::getAuthorizationHeader)
                .map(this::extractToken)
                .filter(this::isTokenValid)
                .map(this::retrieveUserDetails)
                .ifPresent(this::setAuthenticationToContext);
    }

    private boolean isTokenValid(String token) {
        return jwtService.validateToken(token);
    }

    private void setAuthenticationToContext(UserDetails userDetails) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

    private UserDetails retrieveUserDetails(String token) {
        return customUserDetailsService.loadUserByUsername(extractUsername(token));
    }

    private String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    private String extractToken(String header) {
        return header.substring(7);
    }

    @Nullable
    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
