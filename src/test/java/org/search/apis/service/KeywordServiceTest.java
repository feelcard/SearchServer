package org.search.apis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.search.apis.domain.Keyword;
import org.search.apis.repository.KeywordRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("인기 검색어 조회 테스트")
public class KeywordServiceTest {

    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Test
    @DisplayName("인기 검색어 조회 시 내림차순 및 10개 이하 검증")
    void getTopKeywords_isSortedAndSizeLessThanOrEqualToTen() {
        // Prepare test data
        List<Keyword> testKeywords = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            testKeywords.add(new Keyword("test" + i, i));
        }

        // Configure mock behavior
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "count"));
        when(keywordRepository.findAllByOrderByCountDesc(pageable)).thenReturn(new PageImpl<>(testKeywords));

        // Call the method to test
        List<Keyword> topKeywords = keywordService.getTopKeywords(10);

        // Check if the length of the returned list does not exceed 10
        assertTrue(topKeywords.size() <= 10);

        // Check if the count is sorted in descending order
        int previousCount = Integer.MAX_VALUE;
        for (Keyword keyword : topKeywords) {
            int currentCount = keyword.getCount();
            assertTrue(currentCount <= previousCount);
            previousCount = currentCount;
        }
    }
}
