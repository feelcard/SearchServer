package outbound.develop.blog.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Meta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;

}
