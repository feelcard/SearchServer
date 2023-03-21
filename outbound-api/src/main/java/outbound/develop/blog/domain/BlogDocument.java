package outbound.develop.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDocument {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Seoul")
    private ZonedDateTime datetime;


}
