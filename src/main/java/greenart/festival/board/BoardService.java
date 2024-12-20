package greenart.festival.board;

import java.util.List;

public interface BoardService {

    List<BoardDTO> getAllList();

    BoardDTO getBoard(Long boardId);

    List<BoardViewsDTO> getPopularBoards();

    void increaseViewCount(Long boardId);


    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .boardId(boardDTO.getBoardId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .period(boardDTO.getPeriod())
                .location(boardDTO.getLocation())
                .latitude(boardDTO.getLatitude())
                .longitude(boardDTO.getLongitude())
                .contact(boardDTO.getContact())
                .imageUrl(boardDTO.getImageUrl())
                .build();
        return board;
    }

    default BoardDTO entityToDto(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .period(board.getPeriod())
                .location(board.getLocation())
                .latitude(board.getLatitude())
                .longitude(board.getLongitude())
                .contact(board.getContact())
                .imageUrl(board.getImageUrl())
                .build();
        return boardDTO;
    }
}
