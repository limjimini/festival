package greenart.festival.review;

import greenart.festival.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

//    @Test
//    void insertReview(){
//
//        Board board = Board.builder()
//                .boardId(2L).build();
//        Member member = Member.builder()
//                .id(3L).build();
//
//        Review review = Review.builder()
//                .rating(4.5)
//                .depth(1L)
//                .rootNum(5L)
//                .content("TEST REVIEW5")
//                .name("TEST5")
//                .board(board)
//                .member(member)
//                .build();
//
//        reviewRepository.save(review);
//    }
}
