package com.example.micrservice.configs.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(filterName = "transactionLoggingFilter", urlPatterns = "/*")
public class MdcLoggingFilter extends OncePerRequestFilter {
    private static final String TRANSACTION_NAME = "transactionId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String transactionId = request.getHeader(TRANSACTION_NAME);
        MDC.put(TRANSACTION_NAME, transactionId);

        try {
            log.info("Request came: endpoint {} called", request.getRequestURI());
            response.setHeader(TRANSACTION_NAME, transactionId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
