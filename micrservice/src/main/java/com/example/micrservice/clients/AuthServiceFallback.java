package com.example.micrservice.clients;

import com.example.micrservice.models.ResponseAuthorizationModel;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFallback implements AuthServiceClient {
    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        return null;
    }
}
