package org.search.apis.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@WebFilter(urlPatterns = "/*")
public class UrlDecodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Map<String, String[]> decodedParams = new HashMap<>(httpRequest.getParameterMap());

        for (String key : httpRequest.getParameterMap().keySet()) {
            String[] values = httpRequest.getParameterValues(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = URLDecoder.decode(values[i], String.valueOf(StandardCharsets.UTF_8));
            }
            decodedParams.put(key, values);
        }

        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getParameter(String name) {
                String[] values = decodedParams.get(name);
                return values != null && values.length > 0 ? values[0] : null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return decodedParams.get(name);
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return decodedParams;
            }
        };

        chain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
    }
}
