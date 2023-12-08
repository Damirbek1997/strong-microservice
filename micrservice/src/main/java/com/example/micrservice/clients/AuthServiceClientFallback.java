package com.example.micrservice.clients;

import com.example.micrservice.exceptions.UnexpectedException;
import com.example.micrservice.models.ResponseAuthorizationModel;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    private static final String ERROR_MSG = "Auth service is unavailable";

    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        throw new UnexpectedException(ERROR_MSG);
    }
}
