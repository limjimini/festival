package greenart.festival.bookmark.repository;

import greenart.festival.bookmark.dto.BookmarkDTO;
import greenart.festival.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    @Query("select new greenart.festival.bookmark.dto.BookmarkDTO(bm.bookmarkId, bm.email, bm.bookmark, bm.board.boardId, bm.member.id) from Bookmark bm left outer join Member m on bm.member.id = m.id where bm.email =:email and bm.bookmark=true")
    List<BookmarkDTO> findBookmarksByEmail(@Param("email") String email);


    @Query("select new greenart.festival.bookmark.dto.BookmarkDTO(bm.bookmarkId, bm.email, bm.bookmark, bm.board.boardId, bm.member.id) from Bookmark bm left outer join Member m on bm.member.id = m.id left outer join Board b on bm.board.boardId = b.boardId where bm.email =:email and bm.board.boardId =:boardId and bm.bookmark=true")
    BookmarkDTO findBookmarkByEmailAndBoard(@Param("email") String email, @Param("boardId") Long boardId);


}
