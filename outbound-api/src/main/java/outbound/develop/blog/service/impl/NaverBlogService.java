package outbound.develop.blog.service.impl;

import outbound.develop.blog.domain.BlogDTO;
import outbound.develop.blog.domain.BlogDocument;
import outbound.develop.blog.domain.Meta;
import outbound.develop.blog.domain.NaverSearchResult;
import outbound.develop.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 카카오 ,네이버 응답이 모두 없을 경우에 대한 대응답 구현
 * TODO 네이버 API 응답 테스트 구현
 */

@Service
public class NaverBlogService implements BlogService {
    private final RestTemplate restTemplate;
    private final String naverClientId;
    private final String naverClientSecret;

    public NaverBlogService(RestTemplate restTemplate,
                                  @Value("${naver.api.client-id}") String naverClientId,
                                  @Value("${naver.api.client-secret}") String naverClientSecret) {
        this.restTemplate = restTemplate;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
    }

    @Override
    public BlogDTO search(String query, String sort, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverClientSecret);
        headers.add("Accept", "application/xml"); // XML로 응답을 요청합니다.

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        if("accuracy".equals(sort)){
            sort = "sim";
        }else if("recency".equals(sort)){
            sort = "date";
        }

        String url = "https://openapi.naver.com/v1/search/blog";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", query)
                .queryParam("start", page)
                .queryParam("display", size)
                .queryParam("sort", sort.toLowerCase());

        ResponseEntity<NaverSearchResult> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, NaverSearchResult.class);
        NaverSearchResult naverSearchResult = responseEntity.getBody();

        List<BlogDocument> blogDocuments = naverSearchResult.getItems().stream()
                .map(document -> {
                    ZonedDateTime postdate = document.getPostdateAsZonedDateTime();
                    return new BlogDocument(document.getTitle(),document.getDescription(),document.getLink(), document.getBloggername(),"",postdate);
                })
                .collect(Collectors.toList());

        return new BlogDTO("naver",new Meta(naverSearchResult.getTotal(),naverSearchResult.getStart(),
                naverSearchResult.getStart() + naverSearchResult.getDisplay() >= naverSearchResult.getTotal()),blogDocuments);
    }
}
