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
import greenart.festival.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;


    @Override
    public boolean isBookmarked(String email, Long boardId) {
        BookmarkDTO bookmarkDTO = bookmarkRepository.findBookmarkByEmailAndBoard(email, boardId);

        if(bookmarkDTO != null) {
            System.out.println("북마크 추가 상태");
            return true;
        }

        System.out.println("북마크 해제 상태");
        return false;
    }
        

    @Override
    public List<BookmarkDTO> getBookmarks(String email) {
        List<BookmarkDTO> bookmarks = bookmarkRepository.findBookmarksByEmail(email);

        return bookmarks;
    }

    @Override
    @Transactional
    public boolean bookmarking(String email, Long boardId) {
        BookmarkDTO bookmarkDTO = bookmarkRepository.findBookmarkByEmailAndBoard(email, boardId);

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));


        // 보드 정보 가져오기
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("보드가 존재하지 않습니다."));

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
            return false;
        }


        Bookmark bookmark = Bookmark.builder()
                .email(member.getEmail())
                .bookmark(true)
                .board(board)
                .member(member)
                .build();
        bookmarkRepository.save(bookmark);
        System.out.println("북마크 설정");

        return true;
    }
}
