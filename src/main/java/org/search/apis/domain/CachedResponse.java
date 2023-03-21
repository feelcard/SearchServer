package org.search.apis.domain;

import lombok.Getter;

import java.time.Instant;
@Getter
public class CachedResponse {
    private final Object response;
    private Instant expiryTime;

    public CachedResponse(Object response, long expiryTime) {
        this.response = response;
        this.expiryTime = Instant.now().plusMillis(expiryTime*1000);;
    }

    public CachedResponse(Object response, Instant expiryTime) {
        this.response = response;
        this.expiryTime = expiryTime;
    }

    public Object getResponse() {
        return response;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiryTime);
    }
}
