package org.search.apis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.search.apis.domain.Keyword;
import org.search.apis.service.KeywordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonControllerTest {

    @InjectMocks
    private CommonController commonController;

    @Mock
    private KeywordService keywordService;

    @Test
    void getPopularKeywords_lengthDoesNotExceedTen() {
        // Prepare test data
        List<Keyword> testKeywords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testKeywords.add(new Keyword("test" + i, i));
        }

        // Configure mock behavior
        when(keywordService.getTopKeywords(10)).thenReturn(testKeywords);

        // Call the method to test
        List<Keyword> popularKeywords = commonController.getPopularKeywords("test");

        // Check if the length of the returned list does not exceed 10
        assertEquals(10, popularKeywords.size());
    }
}
