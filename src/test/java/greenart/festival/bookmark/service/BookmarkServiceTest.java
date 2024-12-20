package greenart.festival.bookmark.service;

import greenart.festival.board.Board;
import greenart.festival.board.BoardDTO;
import greenart.festival.board.BoardRepository;
import greenart.festival.board.BoardService;
import greenart.festival.bookmark.dto.BookmarkDTO;
import greenart.festival.bookmark.entity.Bookmark;
import greenart.festival.bookmark.repository.BookmarkRepository;
import greenart.festival.member.entity.Member;
import greenart.festival.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkServiceTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardService boardService;

    @Test
    void getBookmarks() {

        List<BookmarkDTO> bookmarkList = bookmarkRepository.findBookmarksByEmail("mpi9904@gmail.com");
        System.out.println("bookmarkList = " + bookmarkList);
    }

    @Test
    void bookmarking() {
        BookmarkDTO bookmarkDTO = bookmarkRepository.findBookmarkByEmailAndBoard("mpi9904@gmail.com", 6L);
        System.out.println("bookmarkDTO = " + bookmarkDTO);

        Member member = memberRepository.findByEmail("mpi9904@gmail.com").get();
        System.out.println("member = " + member);

        BoardDTO boardDTO = boardRepository.findByBoardId(6L);

        Board board = boardService.dtoToEntity(boardDTO);
        System.out.println("board = " + board);





        if(bookmarkDTO != null) {
            Bookmark bookmark = Bookmark.builder()
                    .bookmarkId(bookmarkDTO.getBookmarkId())
                    .email(bookmarkDTO.getEmail())
                    .bookmark(true)
                    .board(board)
                    .member(member)
                    .build();


            bookmarkRepository.delete(bookmark);
            System.out.println("북마크 해제");

            return;
        }

        Bookmark bookmark = Bookmark.builder()
                    .email(member.getEmail())
                    .bookmark(true)
                    .board(board)
                    .member(member)
                    .build();

        bookmarkRepository.save(bookmark);
        System.out.println("북마크 설정");

    }
}