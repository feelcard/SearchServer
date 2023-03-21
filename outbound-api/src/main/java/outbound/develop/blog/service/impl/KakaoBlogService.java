package outbound.develop.blog.service.impl;

import outbound.develop.blog.domain.BlogDTO;
import outbound.develop.blog.service.BlogService;
import outbound.develop.blog.domain.KakaoSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * TODO 카카오 API 응답 테스트 구현
 */
@Service
public class KakaoBlogService implements BlogService {
    private final RestTemplate restTemplate;
    private final String kakaoSearchApiKey;

    @Autowired
    public KakaoBlogService(RestTemplate restTemplate, @Value("${kakao.api.key}") String kakaoSearchApiKey) {
        this.restTemplate = restTemplate;
        this.kakaoSearchApiKey = kakaoSearchApiKey;
    }

    @Override
    public BlogDTO search(String query, String sort, int page, int size) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoSearchApiKey);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = "https://dapi.kakao.com/v2/search/blog";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort.toLowerCase());
        ResponseEntity<KakaoSearchResult> responseEntity =null;

        try{
            responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, KakaoSearchResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        KakaoSearchResult kakaoSearchResult = responseEntity.getBody();
        BlogDTO dto = new BlogDTO("kakao",kakaoSearchResult.getMeta(),kakaoSearchResult.getDocuments());

        return dto;
    }

}
