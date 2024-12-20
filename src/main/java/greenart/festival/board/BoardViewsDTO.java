package greenart.festival.board;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BoardViewsDTO {
    private Long boardId;
    private String title;
    private int views;

    private BigDecimal latitude;
    private BigDecimal longitude;
}
