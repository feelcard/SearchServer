package org.search.apis.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
@Component
public class ApiCallUtils {
    @Value("${time.api-call-timeout}")
    private long timeout;
    public  <T> T callApiWithFallback(Supplier<T> primaryApiCall, Supplier<T> fallbackApiCall) throws Exception {
        try {
            CompletableFuture<T> primaryApiFuture = CompletableFuture.supplyAsync(primaryApiCall);
            return primaryApiFuture.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return fallbackApiCall.get();
        }
    }
}

