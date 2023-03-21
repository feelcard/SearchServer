package org.search.apis.controller;

import org.search.apis.exception.CustomApiException;
import org.search.apis.util.ApiCallUtils;
import org.springframework.http.HttpStatus;
import outbound.develop.blog.domain.BlogDTO;
import outbound.develop.blog.service.BlogService;
import org.search.apis.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/search/v1.0/blog")
public class BlogController {

    ApiCallUtils apiCallUtils;
    private final KeywordService keywordService;
    private final BlogService kakaoBlogService;
    private final BlogService naverBlogService;
    public BlogController(@Qualifier("kakaoBlogService") BlogService kakaoBlogService,
                          @Qualifier("naverBlogService") BlogService naverBlogService,
                          KeywordService keywordService, ApiCallUtils apiCallUtils) {
        this.kakaoBlogService = kakaoBlogService;
        this.naverBlogService = naverBlogService;
        this.keywordService = keywordService;
        this.apiCallUtils = apiCallUtils;
    }


    /**
     * @param query 검색을 원하는 질의어
     * @param sort 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
     * @param page 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
     * @param size 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
     * @return query에 따른 통해 블로그를 검색 결과
     *
     * TODO 각 파라미터에 대해서 호출 시 의도한 에러 응답이 나가는지 테스트 코드 작성
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> search(HttpServletRequest request, @RequestParam String query,
                                    @RequestParam(required = false, defaultValue = "accuracy") String sort,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size) throws Exception {

        String requestURI = request.getRequestURI();
        BlogDTO blogDTO = null;

        if (!sort.equalsIgnoreCase("accuracy") && !sort.equalsIgnoreCase("recency")) {
            throw new CustomApiException(requestURI,"sort 값이 잘못되었습니다.");
        }
        if (page < 1 || page > 50) {
            throw new CustomApiException(requestURI,"page 값이 잘못되었습니다.");
        }
        if (size < 1 || size > 50) {
            throw new CustomApiException(requestURI,"size 값이 잘못되었습니다.");
        }

        keywordService.addSearchCount(query);

            blogDTO = apiCallUtils.callApiWithFallback(
                    () -> kakaoBlogService.search(query, sort, page, size),
                    () -> naverBlogService.search(query, sort, (page - 1) * size + 1, size)
            );

            return ResponseEntity.status(HttpStatus.OK).body(blogDTO.toBlogResponse());
        }

    }


