package com.example.authservice.services;

public interface BruteForceProtectService {
    void loginFailed(String key);
    void resetCache(String key);
    boolean isBlocked(String key);
}
