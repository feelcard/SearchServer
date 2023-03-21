package org.search.apis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.search.apis.domain.CachedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CachingAspect {

    @Value("${time.dto-cache}")
    private long dtoCache;

    @Around("execution(* org.search.apis.controller.*.*(..))")
    public Object cacheApiResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = null;

        // HttpServletRequest 객체 찾기
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request == null) {
            return joinPoint.proceed();
        }

        String cacheKey = CacheManager.createCacheKey(request);
        Object cachedResponse = CacheManager.getFromCache(cacheKey);

        if (cachedResponse != null) {
            return cachedResponse;
        }

        Object response = joinPoint.proceed();
        CacheManager.saveToCache(cacheKey,response,dtoCache); // 캐시를 1분 동안 유지 (단위: 초)
        return response;
    }
}

