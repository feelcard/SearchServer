package outbound.develop.blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class KakaoSearchResult {
    private Meta meta;
    private List<KakaoDocument> documents;

}


