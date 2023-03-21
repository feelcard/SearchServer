package org.search.apis.config;

import org.search.apis.filter.CachingFilter;
import org.search.apis.filter.QueryCheckFilter;
import org.search.apis.service.KeywordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${spring.filter.search-url-pattern}")
    private String searchUrlPattern;

    @Value("${spring.filter.common-api-url-pattern}")
    private String commonApiUrlPattern;

    @Value("${spring.filter.caching-filter-order}")
    private int cachingFilterOrder;

    @Bean
    public FilterRegistrationBean<QueryCheckFilter> queryCheckFilter() {
        FilterRegistrationBean<QueryCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new QueryCheckFilter());
        registrationBean.addUrlPatterns(searchUrlPattern);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CachingFilter> myFilterBean(CachingFilter myFilter) {
        FilterRegistrationBean<CachingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(myFilter);
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

}