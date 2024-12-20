package greenart.festival.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select new greenart.festival.board.BoardDTO(" +
            "b.boardId, b.title, b.content, b.period, b.location, b.latitude, b.longitude, b.contact, b.imageUrl) " +
            "from Board b " +
            "order by b.period")
    List<BoardDTO> findAllList(); // 모든 축제 리스트 가져옴

    @Query("select new greenart.festival.board.BoardDTO(" +
            "b.boardId, b.title, b.content, b.period, b.location, b.latitude, b.longitude, b.contact, b.imageUrl) " +
            "from Board b " +
            "where b.boardId =:boardId")
    BoardDTO findByBoardId(Long boardId); // 아이디를 통해 축제 정보 가져옴

    @Query("select new greenart.festival.board.BoardViewsDTO(" +
            "b.boardId, b.title, b.views, b.latitude, b.longitude) " +
            "from Board b " +
            "order by b.views desc, b.title limit 10")
    List<BoardViewsDTO> findTop10ByViews();

}
