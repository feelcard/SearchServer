package org.search.apis.cache;

import org.search.apis.domain.CachedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static final ConcurrentHashMap<String, CachedResponse> cache = new ConcurrentHashMap<>();

    public static CachedResponse getFromCache(String key) {
        return cache.get(key);
    }

    public static ResponseEntity<?> getCachedResponse(String cacheKey) {
        CachedResponse cachedResponse = cache.get(cacheKey);

        if (cachedResponse != null && !cachedResponse.isExpired()) {
            return ResponseEntity.status(HttpStatus.OK).body(cachedResponse.getResponse());
        } else {
            return null;
        }
    }


    public static void saveToCache(String key, ResponseEntity<?> responseEntity, long expiredTime) {
        Object value = responseEntity.getBody();
        Instant expiryTime = Instant.now().plusMillis(expiredTime*1000);
        CachedResponse cachedResponse = new CachedResponse(value, expiryTime);
        cache.put(key, cachedResponse);
    }


    public static String createCacheKey(HttpServletRequest request) {
        String URI =  request.getRequestURI();
        String query = request.getParameter("query");
        String page = request.getParameter("page") ==null?"1":request.getParameter("page").toString();
        String size = request.getParameter("size") ==null?"1":request.getParameter("size").toString();
        String sort = request.getParameter("sort") ==null?"accuracy":request.getParameter("sort").toString();
        return URI+"_"+query+"_" + page+"_"+size+"_"+sort;
    }
}
