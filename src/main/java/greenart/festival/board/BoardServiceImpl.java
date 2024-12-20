package greenart.festival.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> getAllList() {
//        List<BoardDTO> allList = boardRepository.findAllList();
//
//        Set<String> titles = new HashSet<>();
//        List<BoardDTO> uniqueList = new ArrayList<>();
//
//        for(BoardDTO boardDTO : allList) {
//            if(!titles.contains(boardDTO.getTitle())) {
//                titles.add(boardDTO.getTitle());
//                uniqueList.add(boardDTO);
//            }
//        }
//        return uniqueList;
        return boardRepository.findAllList();
    }

    @Override
    public BoardDTO getBoard(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    @Override
    public List<BoardViewsDTO> getPopularBoards() {
        return boardRepository.findTop10ByViews();
    }

    @Transactional
    @Override
    public void increaseViewCount(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Festival not found"));

        Board increaseBoard = board.toBuilder()
                .views(board.getViews() + 1)
                .build();
        boardRepository.save(increaseBoard);
    }

}
