package greenart.festival.review.service;

import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.review.dto.CommentDTO;
import greenart.festival.review.dto.ReviewDTO;
import greenart.festival.review.dto.ReviewResponseDTO;
import greenart.festival.review.entity.Review;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;


public interface ReviewService {

    //등록
    void addReview(ReviewDTO reviewDTO, Long boardId, Member member);

    //조회
    List<CommentDTO> getReviews(Long boardId);

    //수정
    String modifyReview(ReviewDTO reviewDTO, Long reviewId, Member member);

    //삭제
    String deleteReview(Long reviewId, Member member);

    //별점 평균
    Double getAverageRatingByBoard(Long boardId);

    //리뷰 카운트
    Long getReviewCountByBoard(Long boardId);

    //대댓글 등록
    void addReplyAtReview(Long rootNumId, ReviewDTO reviewDTO, Member member);



    default Review dtoToEntity(ReviewDTO reviewDTO) {

        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .build();
        return review;
    }

    default ReviewDTO entityToDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .build();

        return reviewDTO;
    }

}
