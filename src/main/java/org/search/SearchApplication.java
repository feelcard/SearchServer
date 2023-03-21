package org.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"org.search.*", "outbound.develop.*"})
public class SearchApplication {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        SpringApplication.run(SearchApplication.class, args);

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
