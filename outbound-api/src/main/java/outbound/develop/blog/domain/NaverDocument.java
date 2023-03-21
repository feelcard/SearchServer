package outbound.develop.blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NaverDocument {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    @XmlElement(name = "postdate")
    private String postdate;

    public ZonedDateTime getPostdateAsZonedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(postdate, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        return zonedDateTime;
    }

    // 생성자, 게터 및 세터를 여기에 추가합니다.
}
