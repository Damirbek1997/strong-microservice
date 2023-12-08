package com.example.micrservice.configs.filters;

import com.example.micrservice.clients.AuthServiceClient;
import com.example.micrservice.models.ResponseAuthorizationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            putUserToContext(request);
        } catch (Exception ex) {
            log.error("Something went wrong, error {}", ex.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void putUserToContext(HttpServletRequest request) {
        Optional.of(request)
                .map(this::getAuthorizationHeader)
                .map(this::extractToken)
                .map(this::getAuthorities)
                .ifPresent(responseAuthorizationModel ->
                        setAuthenticationToContext(responseAuthorizationModel.getUsername(), convertAuthorities(responseAuthorizationModel.getAuthorities())));
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String extractToken(String header) {
        return header.substring(7);
    }

    private ResponseAuthorizationModel getAuthorities(String token) {
        return authServiceClient.getAuthorities(token);
    }

    private Set<SimpleGrantedAuthority> convertAuthorities(String authorities) {
        return Arrays.stream(authorities.split(","))
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private void setAuthenticationToContext(String username, Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }
}
