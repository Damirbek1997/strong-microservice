package com.example.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class TransactionIdFilter implements GlobalFilter, Ordered {
    private static final String TRANSACTION_NAME = "transactionId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String transactionId = generateTransactionId();

        log.info("Request came: endpoint {} called", exchange.getRequest().getPath());
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(TRANSACTION_NAME, transactionId)
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}