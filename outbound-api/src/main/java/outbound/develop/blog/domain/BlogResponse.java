package outbound.develop.blog.domain;

import lombok.*;
import outbound.develop.blog.domain.Meta;

import java.util.List;

/**
 * BlogDTO 캐싱 후 expireTime을 사용자에게 return 시키지 않기 위한 DTO
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BlogResponse {
    private final String state;
    private final String origin;
    private final Meta meta;
    private final List<?> documents;

}