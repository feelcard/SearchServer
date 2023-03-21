package org.search.apis.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.search.apis.domain.ErrorDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String query = req.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            ObjectMapper objectMapper = new ObjectMapper();
            ErrorDTO errorDTO = ErrorDTO.getInstance();
            errorDTO.setUrl(req.getRequestURI());
            errorDTO.setMessage("필수 값을 확인해주세요");
            res.getWriter().write(objectMapper.writeValueAsString(errorDTO));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}