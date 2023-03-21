package outbound.develop.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import util.ZonedDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime datetime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));


}
