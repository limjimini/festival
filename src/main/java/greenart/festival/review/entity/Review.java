package greenart.festival.review.entity;

import greenart.festival.board.Board;
import greenart.festival.entity.BaseEntity;
import greenart.festival.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString(exclude = {"board","member"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content; //리뷰 내용
    private Double rating; //별점
    private Long depth; // 댓글 깊이 필드
    private Long rootNum;   // 댓글 - 대댓글 관계
    private String email;    // 회원 이메일


    public void changeReview(String content, Double rating){
        this.content = content;
        this.rating = rating;
    }

}
