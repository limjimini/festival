package greenart.festival.board;

import greenart.festival.bookmark.repository.BookmarkRepository;
import greenart.festival.bookmark.service.BookmarkService;
import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookmarkService bookmarkService;



    @GetMapping("/list")
    public List<BoardDTO> getList() {
        return boardService.getAllList();
    }

    @GetMapping("/popular")
    public List<BoardViewsDTO> getPopularBoards() {
        return boardService.getPopularBoards();
    }

    @PostMapping("/popular/{boardId}/view")
    public ResponseEntity<Void> increaseViewCount(@PathVariable Long boardId) {
        boardService.increaseViewCount(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/map/{boardId}/bookmark")
    public ResponseEntity<Map<String, Object>> bookmark(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal MemberAuthDTO memberAuthDTO) {
        Member member = memberService.dtoToEntity(memberAuthDTO);

        System.out.println("member = " + member);
        System.out.println("boardId = " + boardId);

        boolean isBookmarked = bookmarkService.bookmarking(member.getEmail(), boardId);

        Map<String, Object> response = new HashMap<>();
        response.put("isBookmarked", isBookmarked);
        response.put("message", isBookmarked ? "북마크가 추가되었습니다." : "북마크가 제거되었습니다.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/map/{boardId}/bookmark-status")
    public ResponseEntity<Map<String, Object>> getBookmarkStatus(@PathVariable("boardId") Long boardId,
                                                                 @AuthenticationPrincipal MemberAuthDTO memberAuthDTO) {
        Member member = memberService.dtoToEntity(memberAuthDTO);

        boolean isBookmarked = bookmarkService.isBookmarked(member.getEmail(), boardId);

        Map<String, Object> response = new HashMap<>();
        response.put("isBookmarked", isBookmarked);

        return ResponseEntity.ok(response);
    }
}
