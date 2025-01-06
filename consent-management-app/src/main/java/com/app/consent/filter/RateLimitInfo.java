package com.app.consent.filter;

import lombok.Getter;

@Getter
public class RateLimitInfo {

    private final long startTime;
    private       int  requestCount;

    public RateLimitInfo(long startTime, int initialCount) {
        this.startTime = startTime;
        this.requestCount = initialCount;
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }
}