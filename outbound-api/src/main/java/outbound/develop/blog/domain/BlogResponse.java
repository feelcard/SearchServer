package outbound.develop.blog.domain;

import lombok.*;
import outbound.develop.blog.domain.Meta;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BlogDTO 캐싱 후 expireTime을 사용자에게 return 시키지 않기 위한 DTO
 */
@Getter
@EqualsAndHashCode
public class BlogResponse {

    public BlogResponse(String state, String origin, Meta meta, List<BlogDocument> documents) {
        this.state = state;
        this.origin = origin;
        this.meta = meta;
        this.documents = documents;
    }

    private final String state;
    private final String origin;
    private final Meta meta;
    private final List<BlogDocument> documents;

}