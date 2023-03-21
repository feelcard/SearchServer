package org.search.apis.controller;

import org.search.apis.domain.Keyword;
import org.search.apis.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keyword/v1.0")
public class CommonController {

    private final KeywordService keywordService;

    @Autowired
    public CommonController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     *
     * @return 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
     *
     * TODO 검색 카테고리가 늘어날 경우 Keyword의 내용에따라 카테고리별 순서가 제공되도록 구현
     *
     */
    @GetMapping("{keyword}/popular")
    public List<Keyword> getPopularKeywords(@PathVariable String keyword) {
        return keywordService.getTopKeywords(10);
    }
}
