package com.example.authservice.services.impl;

import com.example.authservice.services.BruteForceProtectService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class BruteForceProtectServiceImpl implements BruteForceProtectService {
    private final LoadingCache<String, Integer> attemptsCache;

    @Value("${protect.attempt.brute-force}")
    private int maxAttempt;

    public BruteForceProtectServiceImpl() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(final String key) {
                        return 0;
                    }
                });
    }

    @Override
    public void loginFailed(String key) {
        int attempts;

        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }

        attempts++;
        attemptsCache.put(key, attempts);
    }

    @Override
    public void resetCache(String key) {
        attemptsCache.put(key, 0);
    }

    @Override
    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= maxAttempt;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
