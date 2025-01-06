package com.app.consent.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimitingFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RateLimitingFilter.class);

    private static final int    MAX_REQUESTS                  = 10; // Maximum allowed requests per window
    private static final long   TIME_WINDOW_MS                = 60 * 1000L; // 1 minute
    private static final int    HTTP_STATUS_TOO_MANY_REQUESTS = 429; // HTTP 429 status code
    private static final String RATE_LIMIT_EXCEEDED_MESSAGE   = "{\"error\": \"Rate limit exceeded. Try again later.\"}";

    private final ConcurrentMap<String, RateLimitInfo> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (isHttpRequest(request, response)) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String clientIp = httpRequest.getRemoteAddr();

            if (isRateLimited(clientIp)) {
                handleRateLimitExceeded(httpResponse);
                return;
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            LOG.error("Exception during request processing: {}", e.getMessage());
        }
    }

    private boolean isHttpRequest(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    boolean isRateLimited(String clientIp) {
        RateLimitInfo rateLimitInfo = requestCounts.compute(clientIp, (key, value) -> {
            long currentTime = Instant.now().toEpochMilli();
            if (value == null || isWindowExpired(value, currentTime)) {
                return new RateLimitInfo(currentTime, 1);
            } else {
                value.incrementRequestCount();
                return value;
            }
        });

        return rateLimitInfo.getRequestCount() > MAX_REQUESTS;
    }

    private boolean isWindowExpired(RateLimitInfo info, long currentTime) {
        return currentTime - info.getStartTime() >= TIME_WINDOW_MS;
    }

    private void handleRateLimitExceeded(HttpServletResponse response) {
        try {
            response.setStatus(HTTP_STATUS_TOO_MANY_REQUESTS);
            response.setContentType("application/json");
            response.getWriter().write(RATE_LIMIT_EXCEEDED_MESSAGE);
        } catch (IOException e) {
            LOG.error("Exception while writing response: {}", e.getMessage());
        }
    }
}