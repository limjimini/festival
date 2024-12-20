package greenart.festival.review.repository;

import greenart.festival.board.Board;
import greenart.festival.member.entity.Member;
import greenart.festival.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Review r where r.member =:member")
    void deleteByMember(Member member);

    @Query("SELECT r FROM Review r WHERE r.board = :board")
    List<Review> findByBoard(Board board);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.board = :board and r.depth = :depth")
    Long countByBoard(Board board, Long depth);

    @Modifying
    @Query(value = "INSERT INTO review (content, rating, name, depth, root_num, board_id, member_id) " +
            "SELECT :#{#review.content}, :#{#review.rating}, :#{#review.name}, :#{#review.depth}, :#{#review.rootNum}, " +
            "b.board_id, m.member_id " +
            "FROM board b LEFT JOIN member m ON m.member_id = :#{#review.member.id} " +
            "WHERE b.board_id = :#{#review.board.boardId}", nativeQuery = true)
    void saveWithJoin(@Param("review") Review review);


}
