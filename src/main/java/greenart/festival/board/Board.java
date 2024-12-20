package greenart.festival.board;

import greenart.festival.member.entity.Member;
import greenart.festival.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "CLOB")
    private String content;

    private String period;

    private String location; // 위치

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;  // 위도

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude; // 경도

    private String contact;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(nullable = false)
    private Integer views = 0; // 조회수: 0(기본값)


//    @Builder.Default
//    private Integer bookmark = 0;   // 북마크: 0(기본값)

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
}
