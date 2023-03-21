package outbound.develop.blog.domain;


import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@AllArgsConstructor
public class BlogDTO {
    private final Instant expiryTime;
    public boolean isExpired() {
        return Instant.now().isAfter(expiryTime);
    }

    public BlogDTO(String origin, Meta meta, List<BlogDocument> documents) {
        this.origin = origin;
        this.meta = meta;
        this.documents =documents;
        this.expiryTime = Instant.now().plusMillis(30000);
    }


    private final String state = "SUCCESS";
    private String origin;
    private Meta meta;
    private List<BlogDocument> documents;

    public String getState() {
        return state;
    }

    public String getOrigin() {
        return origin;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<BlogDocument> getDocuments() {
        return documents;
    }

    public BlogResponse toBlogResponse() {
        return new BlogResponse(state, origin, meta, documents);
    }
}
