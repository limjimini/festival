package greenart.festival.review.controller;

import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import greenart.festival.member.repository.MemberRepository;
import greenart.festival.member.service.MemberService;
import greenart.festival.review.dto.CommentDTO;
import greenart.festival.review.dto.ReviewDTO;
import greenart.festival.review.dto.ReviewResponseDTO;
import greenart.festival.review.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/festival/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;



    @PostMapping("/add/{boardId}")
    public void addReview(@PathVariable Long boardId,
                          @AuthenticationPrincipal MemberAuthDTO memberAuthDTO,
                          @RequestBody ReviewDTO reviewDTO) {

        Member member = memberRepository.findByEmail(memberAuthDTO.getEmail()).get();

        Social provider = member.getProvider();
        System.out.println("provider = " + provider);
        if(member != null) {

            reviewService.addReview(reviewDTO, boardId, member);

        } else{
//            null 검사, "로그인 후 이용해 주세요" 띄우기
              throw new IllegalArgumentException("로그인 후 이용해주세요");
        }
    }

    @GetMapping("/get/{boardId}") //조회
    public ResponseEntity<?> getReviews(@PathVariable Long boardId,
                                        Model model) {

        List<CommentDTO> reviews = reviewService.getReviews(boardId);
        model.addAttribute("reviews", reviews);

        if( reviews == null || reviews.isEmpty() ) {
            return ResponseEntity.ok(Collections.singletonMap("reviews", Collections.emptyList()));
        }
        return ResponseEntity.ok(Collections.singletonMap("reviews", reviews));

    }


    @PutMapping("/modify/{reviewId}") //수정
    public String modifyReview(@PathVariable Long reviewId,
                               @AuthenticationPrincipal MemberAuthDTO memberAuthDTO,
                               @RequestBody ReviewDTO reviewDTO) {

        Member member = memberService.dtoToEntity(memberAuthDTO);

        if(member != null) {

            return reviewService.modifyReview(reviewDTO,reviewId,member);
        }

        return "로그인 확인";
    }

    @DeleteMapping("/delete/{reviewId}") //삭제
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId,
                               @AuthenticationPrincipal MemberAuthDTO memberAuthDTO) {
        System.out.println("memberAuthDTO = " + memberAuthDTO);

        if (memberAuthDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인을 확인하여 주시길 바랍니다.");
        }

        Member member = memberService.dtoToEntity(memberAuthDTO);

        try {
            // 댓글 삭제 서비스 호출
            String result = reviewService.deleteReview(reviewId, member);
            return ResponseEntity.ok(result); // 성공 응답
        } catch (IllegalArgumentException e) {
            // 삭제 요청에 잘못된 값
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            // 기타 서버 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 삭제 중 문제가 발생했습니다.");
        }
    }

    @GetMapping("/avg/{boardId}") //별점 평균
    public Double getAverageRatingByBoard(@PathVariable Long boardId) {

        return reviewService.getAverageRatingByBoard(boardId);
    }

    @GetMapping("/count/{boardId}") //게시물당 리뷰 갯수
    public Long getReviewCountByBoard(@PathVariable Long boardId){

        return reviewService.getReviewCountByBoard(boardId);
    }

    @PostMapping("/reply/{rootNumId}") //대댓 달기
    public void addReplyAtReview(@PathVariable Long rootNumId,
                                 @RequestBody ReviewDTO reviewDTO,
                                 @AuthenticationPrincipal Member member){

        reviewService.addReplyAtReview(rootNumId, reviewDTO, member);
    }
}
