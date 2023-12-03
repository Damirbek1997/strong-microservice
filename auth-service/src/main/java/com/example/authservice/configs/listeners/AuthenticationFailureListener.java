package com.example.authservice.configs.listeners;

import com.example.authservice.services.BruteForceProtectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final BruteForceProtectService bruteForceProtectService;
    private final HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = request.getParameter("username");
        bruteForceProtectService.loginFailed(username);
        log.warn("Bad credentials for user {}", username);
    }
}
