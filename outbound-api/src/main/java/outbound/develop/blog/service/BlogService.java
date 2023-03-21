package outbound.develop.blog.service;


import outbound.develop.blog.domain.BlogDTO;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {
    BlogDTO search(String query, String sort, int page, int size);
}
