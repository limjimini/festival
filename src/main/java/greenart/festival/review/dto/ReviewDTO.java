package greenart.festival.review.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReviewDTO {
    private String content; //리뷰 내용
    private Double rating; //별점 (소숫점 사용)
   // private Long depth; // 댓글 단계 구분 필드
}
