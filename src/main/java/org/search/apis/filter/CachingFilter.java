package org.search.apis.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.search.apis.cache.CacheManager;
import org.search.apis.domain.CachedResponse;
import org.search.apis.exception.CustomApiException;
import org.search.apis.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import outbound.develop.blog.domain.BlogDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CachingFilter implements Filter {

    @Autowired
    KeywordService keywordService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String cacheKey = CacheManager.createCacheKey(httpRequest);

        // 캐시에 저장된 결과를 가져옵니다.
        ResponseEntity<?> cachedResponse = CacheManager.getCachedResponse(cacheKey);
        String query = httpRequest.getParameter("query");

        if (cachedResponse == null) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            httpResponse.getWriter().write(objectMapper.writeValueAsString(cachedResponse.getBody()));
            keywordService.addSearchCount(query);


        }

    }

    @Override
    public void destroy() {
    }



}

