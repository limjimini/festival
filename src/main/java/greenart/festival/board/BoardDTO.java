package greenart.festival.board;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String period;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String contact;
    private String imageUrl;
}
