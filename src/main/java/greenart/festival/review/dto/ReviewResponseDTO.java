package greenart.festival.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewResponseDTO {

//    private Long id;
    private String content; //리뷰 내용
    private String name; //글쓴이
    private Double rating; //별점
    private LocalDateTime regDate;  // 댓글 작성시간
    private Long depth; // 댓글 깊이 필드
    private Long rootNum; // 어떤 댓글에 달린 대댓글인가.
//    private Long boardId;



}
