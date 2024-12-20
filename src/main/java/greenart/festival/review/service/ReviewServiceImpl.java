package greenart.festival.review.service;

import greenart.festival.board.Board;
import greenart.festival.board.BoardDTO;
import greenart.festival.board.BoardRepository;
import greenart.festival.board.BoardService;
import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.repository.MemberRepository;
import greenart.festival.member.service.MemberService;
import greenart.festival.review.dto.CommentDTO;
import greenart.festival.review.dto.ReviewDTO;
import greenart.festival.review.dto.ReviewResponseDTO;
import greenart.festival.review.entity.Review;
import greenart.festival.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Override //리뷰 등록
    public void addReview(ReviewDTO reviewDTO, Long boardId, Member member) {

        BoardDTO boardDTO = boardRepository.findByBoardId(boardId);
        Board board = boardService.dtoToEntity(boardDTO);

        if (board == null) {
            throw new IllegalArgumentException("다음 아이디를 가진 게시물이 존재하지 않습니다. [" + boardId + "번]");
        }


        System.out.println("Board = " + board);


        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .email(member.getEmail())
                .depth(0L)
                .board(board)
                .member(member)
                .build();

        reviewRepository.saveAndFlush(review);

        System.out.println("member = " + member);

        review.setRootNum(review.getReviewId());
        
        reviewRepository.save(review);

        System.out.println("review = " + review);
    }

    @Transactional
    @Override //수정(작성 본인만 가능하도록 수정하기~~!)
    public String modifyReview(ReviewDTO reviewDTO, Long reviewId, Member member) {

        Optional<Review> result = reviewRepository.findById(reviewId);

        if(result.isPresent()){

            Review review = result.get();
            Member writer = review.getMember();

            if(writer.getEmail().equals(member.getEmail())){

                review.changeReview(reviewDTO.getContent(), reviewDTO.getRating());
                reviewRepository.save(review);

                return "수정 성공";
            }
        }
        return "수정 실패";
    }

    @Transactional
    @Override //삭제(본인만 삭제 가능하도록 하기~~!)
    public String deleteReview(Long reviewId, Member member) {

        Optional<Review> result = reviewRepository.findById(reviewId);

        if(result.isPresent()){
            Review review = result.get();
            Member writer = review.getMember();

            if(writer.getEmail().equals(member.getEmail())){

                reviewRepository.deleteById(reviewId);

                return "삭제 성공";
            }
        }
        return "삭제 실패";
    }

    @Transactional
    @Override //리뷰 조회
    public List<CommentDTO> getReviews(Long boardId) {

        BoardDTO boardDTO = boardRepository.findByBoardId(boardId);
        Board board = boardService.dtoToEntity(boardDTO);

        if(board == null){
            throw new IllegalArgumentException("다음 아이디를 갖는 게시물이 존재하지 않습니다 " + boardId + "번");
        }

        List<Review> reviews = reviewRepository.findByBoard(board);
        System.out.println("reviews = " + reviews);

        List<CommentDTO> dtos = new ArrayList<>();

        for(Review review : reviews){

            CommentDTO commentDTO = CommentDTO.builder()
                    .reviewId(review.getReviewId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .rootNum(review.getRootNum())
                    .depth(review.getDepth())
                    .regDate(review.getRegDate())
                            .build();

//            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
//
//            reviewResponseDTO.setName(review.getMember().getName());
//            reviewResponseDTO.setRating(review.getRating());
//            reviewResponseDTO.setContent(review.getContent());
//            reviewResponseDTO.setRegDate(review.getRegDate());
//            reviewResponseDTO.setDepth(review.getDepth());
//            reviewResponseDTO.setRootNum(review.getRootNum());
            dtos.add(commentDTO);
        }

        return dtos;

    }

      //게시물에 등록된 후기 갯수
    @Override
    public Long getReviewCountByBoard(Long boardId) {

        BoardDTO boardDTO = boardRepository.findByBoardId(boardId);
        Board board = boardService.dtoToEntity(boardDTO);

        List<Review> reviews = reviewRepository.findByBoard(board);

        for(Review review : reviews){
            Long depth = review.getDepth();
            if(depth == 0){
                System.out.println("댓글입니다");
                Optional<Board> OptBoard = Optional.ofNullable(board);
                return reviewRepository.countByBoard(OptBoard.get(),depth);

            } else if (depth == 1) {
                System.out.println("대댓글입니다");
                Optional<Board> OptBoard = Optional.ofNullable(board);
                return reviewRepository.countByBoard(OptBoard.get(),depth);
            }
        }
        return null;
    }

    // 특정 게시물(여행지)의 별점 평균 조회
    @Transactional
    @Override
    public Double getAverageRatingByBoard(Long boardId) {

        BoardDTO boardDTO = boardRepository.findByBoardId(boardId);
        Board board = boardService.dtoToEntity(boardDTO);

        Optional<Board> OptBoard = Optional.ofNullable(board);

        List<Review> reviewList = reviewRepository.findByBoard(OptBoard.get());

        reviewList.toString();

        if (reviewList.isEmpty()) {
            return null;
        } //리뷰가 없는 경우

        //리뷰 별점 모두 더하기 / 리뷰 카운트
        //but depth = 0만! (별점이 존재하는 원글만 뽑아내기)

        double sumRatings = reviewList.stream()
                .filter(review -> review.getDepth() != null && review.getDepth() == 0)
                .filter(review -> review.getRating() != null)
                .mapToDouble(Review::getRating)
                .sum();

        double count = reviewList.stream()
                .filter(review -> review.getDepth() != null && review.getDepth() == 0)
                .filter(review -> review.getRating() != null)
                .count();

        double avgRating = sumRatings / count;
        double result = Math.round(avgRating * 10.0) / 10.0;

        return result;

    }

    //대댓달기
    @Override
    public void addReplyAtReview(Long rootNumId, ReviewDTO reviewDTO, Member member) {

        Optional<Review> result = reviewRepository.findById(rootNumId);

        if(result.isPresent()) {
            Review review = result.get();
            Long depth = review.getDepth();

            Review reply = Review.builder()
                    .content(reviewDTO.getContent())
                    .board(review.getBoard())
                    .email(member.getEmail())
                    .member(member)
                    .rootNum(rootNumId)
                    .depth(depth+1)
                    .build();

            reviewRepository.save(reply);
        }
    }
}
