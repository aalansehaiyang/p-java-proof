package course.p14.p18;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;

/**
 * @author 微信公众号：微观技术
 * 快照
 */
@Data
@AllArgsConstructor
public class Memento {
    private Long id;
    private String productName;
    private String picture;
}
