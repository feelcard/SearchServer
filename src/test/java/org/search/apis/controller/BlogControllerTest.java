package org.search.apis.controller;

import org.junit.jupiter.api.DisplayName;
import org.search.apis.exception.CustomApiException;
import org.search.apis.service.KeywordService;
import org.search.apis.util.ApiCallUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import outbound.develop.blog.domain.BlogDTO;
import outbound.develop.blog.domain.BlogDocument;
import outbound.develop.blog.domain.Meta;
import outbound.develop.blog.service.BlogService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 컨트롤러 테스트")
class BlogControllerTest {

    @InjectMocks
    private BlogController blogController;

    private ApiCallUtils apiCallUtils;

    @Mock
    private KeywordService keywordService;

    @Mock
    private BlogService kakaoBlogService;

    @Mock
    private BlogService naverBlogService;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        apiCallUtils = mock(ApiCallUtils.class);
        request = new MockHttpServletRequest();
        request.setServletPath("/api/search/v1.0/blog");
        blogController = new BlogController(kakaoBlogService,naverBlogService,keywordService,apiCallUtils);
    }

    @Test
    @DisplayName("블로그 응답 전문 변환 테스트")
    void search_Success() throws Exception {
        String query = "test";
        String sort = "accuracy";
        int page = 1;
        int size = 10;

        Instant expiryTime = Instant.now().plusMillis(30000);
        BlogDTO expectedBlogDTO = new BlogDTO(expiryTime, "origin", new Meta(), new ArrayList<BlogDocument>());

        when(apiCallUtils.callApiWithFallback(any(), any())).thenReturn(expectedBlogDTO);

        ResponseEntity<?> responseEntity = blogController.search(request, query, sort, page, size);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedBlogDTO.toBlogResponse(), responseEntity.getBody());

        verify(keywordService).addSearchCount(query);
    }


    @Test
    @DisplayName("sort 값 검증 테스트 (accuracy 또는 recency 여야 함)")
    void search_InvalidSort() {
        String query = "테스트";
        String sort = "invalid";
        int page = 1;
        int size = 10;

        assertThrows(CustomApiException.class, () -> blogController.search(request, query, sort, page, size));
    }

    @Test
    @DisplayName("page 값 검증 테스트 ( page <= 50 )")
    void search_InvalidLargePage() {
        String query = "테스트";
        String sort = "accuracy";
        int page = 51;
        int size = 10;

        assertThrows(CustomApiException.class, () -> blogController.search(request, query, sort, page, size));
    }

    @Test
    @DisplayName("page 값 검증 테스트 ( page >= 0 )")
    void search_InvalidSmallPage() {
        String query = "테스트";
        String sort = "accuracy";
        int page = -1;
        int size = 10;

        assertThrows(CustomApiException.class, () -> blogController.search(request, query, sort, page, size));
    }

    @Test
    @DisplayName("size 값 검증 테스트 ( size <= 50 )")
    void search_InvalidLargeSize() {
        String query = "테스트";
        String sort = "accuracy";
        int page = 1;
        int size = 51;

        assertThrows(CustomApiException.class, () -> blogController.search(request, query, sort, page, size));
    }

    @Test
    @DisplayName("size 값 검증 테스트 ( size >= 0 )")
    void search_InvalidSmallSize() {
        String query = "테스트";
        String sort = "accuracy";
        int page = 1;
        int size = -1;

        assertThrows(CustomApiException.class, () -> blogController.search(request, query, sort, page, size));
    }
}
