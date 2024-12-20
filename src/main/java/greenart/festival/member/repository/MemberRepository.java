package greenart.festival.member.repository;

import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import greenart.festival.review.dto.CommentDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email=:email")
    Optional<Member> findByEmail(@Param("email") String email, @Param("provider") Social provider);


    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email=:email")
    Optional<Member> findByEmail(@Param("email") String email);



    //마이페이지 댓글조회(이메일 -> 댓글List 불러오기)
    @Query("select new greenart.festival.review.dto.CommentDTO(r.reviewId, r.content, r.rating ,r.rootNum, r.depth, r.board.boardId, r.regDate) from Member m left join Review r on m.email = r.email where m.email = :email")
    List<CommentDTO> findCommentIdsByEmail(@Param("email") String email);


//    Optional<Member> findById(Long memberId);//findById Long타입
//
//    boolean existsByEmail(String newEmail);
//
//    //241202추가된부분
//    Optional<Member> findByLoginId(String loginId);
//
//    Optional<Member> findById(String email);
//
//    //비밀번호변경 업데이트쿼리
//    @Modifying
//    @Transactional
//    @Query("UPDATE Member m SET m.password = :password WHERE m.email = :email")
//    void updatePassword(String email, String password);
}

