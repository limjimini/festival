package greenart.festival.review.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CommentDTO {

    private Long reviewId;
    private String content;
    private Double rating;
    private Long rootNum;
    private Long depth;
    private Long boardId;
    private LocalDateTime regDate;
}
