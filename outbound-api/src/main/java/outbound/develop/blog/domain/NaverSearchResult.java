package outbound.develop.blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class NaverSearchResult {


    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "total")
    private int total;

    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "start")
    private int start;

    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "display")
    private int display;

    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "item")
    private List<NaverDocument> items;

}
