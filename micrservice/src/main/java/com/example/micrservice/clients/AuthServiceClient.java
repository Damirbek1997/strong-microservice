package com.example.micrservice.clients;

import com.example.micrservice.models.ResponseAuthorizationModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8081", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
    @GetMapping("/auth/authorities")
    ResponseAuthorizationModel getAuthorities(@RequestParam String token);
}
