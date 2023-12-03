package com.example.authservice.configs.listeners;

import com.example.authservice.services.BruteForceProtectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final BruteForceProtectService bruteForceProtectService;
    private final HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = request.getParameter("username");
        bruteForceProtectService.resetCache(username);
    }
}
