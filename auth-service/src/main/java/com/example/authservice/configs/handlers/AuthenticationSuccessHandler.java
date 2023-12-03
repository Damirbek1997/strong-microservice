package com.example.authservice.configs.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String LAST_USERNAME_KEY = "LAST_USERNAME";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute(LAST_USERNAME_KEY, request.getParameter("username"));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
