package org.search.apis.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.search.apis.domain.ErrorDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@DisplayName("필수 값 (query a.k.a 검색어) 검증 필터 테스트")
class QueryCheckFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private QueryCheckFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new QueryCheckFilter();
    }

    @Test
    @DisplayName("필수 값 정상일 시 필터 정상 동작 테스트")
    void testDoFilter_ValidQuery() throws IOException, ServletException {
        when(request.getParameter("query")).thenReturn("test");

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilter_InvalidQuery() throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("query")).thenReturn("");
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestURI()).thenReturn("/test-uri");

        filter.doFilter(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(chain, never()).doFilter(request, response);

        writer.flush();
        String responseJson = stringWriter.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDTO errorDTO = objectMapper.readValue(responseJson, ErrorDTO.class);

        assertEquals("/test-uri", errorDTO.getUrl());
        assertEquals("필수 값을 확인해주세요", errorDTO.getMessage());
    }
}

