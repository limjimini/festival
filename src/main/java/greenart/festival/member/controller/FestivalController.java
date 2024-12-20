package greenart.festival.member.controller;

import greenart.festival.board.BoardDTO;
import greenart.festival.board.BoardRepository;
import greenart.festival.board.BoardService;
import greenart.festival.board.BoardViewsDTO;
import greenart.festival.bookmark.dto.BookmarkDTO;
import greenart.festival.bookmark.service.BookmarkService;
import greenart.festival.member.dto.LoginDTO;
import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.dto.MemberRegisterDTO;
import greenart.festival.member.dto.MyPageDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.service.MailService;
import greenart.festival.member.service.MemberService;
import greenart.festival.review.dto.CommentDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/festival")
public class FestivalController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MailService mailService;

    @Autowired
    private BookmarkService bookmarkService;

    private int verificationCode;

    // step1 : 약관동의
    @GetMapping("/step1")
    public String step1() {
        return "/festival/step1";
    }


    @PostMapping("/step1")
    public String postStep1(@RequestParam(value = "agreement", required = false) List<String> agreements) {
        if(agreements == null || agreements.size()<2) {
            return "/festival/step1";    // 약관 미동의 -> step1 이동
        }

        return "redirect:/festival/step2";  // step2 이동
    }


    // step2 : 이메일 인증
    @GetMapping("/step2")
    public String step2() {
        return "/festival/step2";
    }

    // 인증 메일 전송
    @GetMapping("/mailSend")
    @ResponseBody
    public ResponseEntity<?> mailSend(@RequestParam String email){
        HashMap<String, Object> response = new HashMap<>();

        try {
            verificationCode = mailService.sendMail(email);

            response.put("success", true);

        }catch (Exception e){
            response.put("success", false);
            response.put("message", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    // 인증 번호 확인
    @GetMapping("/mailCheck")
    @ResponseBody
    public ResponseEntity<?> mailCheck(@RequestParam String code){
        boolean isMatch = code.equals(String.valueOf(verificationCode));

        return ResponseEntity.ok(isMatch);
    }


    @PostMapping("/step2")
    public String postStep2(String email, RedirectAttributes rttr) {
//        rttr.addFlashAttribute("register", email);
        rttr.addAttribute("email", email);
        return "redirect:/festival/register";
    }

    
    // 회원 가입
    @GetMapping("/register")
    public String register(@ModelAttribute("member") MemberRegisterDTO dto) {
        return "/festival/register"; // 회원가입 작성 페이지
    }


    @PostMapping("/register")
    public String registerPost(@ModelAttribute("member") MemberRegisterDTO dto, RedirectAttributes rttr) {
        Long memberId = memberService.register(dto);
        rttr.addFlashAttribute("register", memberId);
        return "redirect:/festival/home";
    }



    // 홈페이지
    @GetMapping("/home")
    public String home(Model model,
                       @AuthenticationPrincipal MemberAuthDTO memberAuthDTO) {


        Object register = model.getAttribute("register");
        System.out.println("memberId = " + register);

        System.out.println("memberAuthDTO = " + memberAuthDTO);

        List<BoardDTO> boardList = new ArrayList<>();

        List<BoardViewsDTO> popularBoards = boardService.getPopularBoards();

        for (BoardViewsDTO boardViews : popularBoards) {
            Long boardId = boardViews.getBoardId();
            BoardDTO board = boardService.getBoard(boardId);

            if(board != null) {
                boardList.add(board);
            }

            model.addAttribute("boardList", boardList);
        }

        return "/festival/home";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal Member member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해 주세요.");
        }
        return ResponseEntity.ok("정상 접근: " + member.getEmail());
    }

//    @PostMapping("/idCheck")
//    @ResponseBody
//    public ResponseEntity<Boolean> idDuplicateCheck(String loginId) {
//        MemberRegisterDTO dto = memberService.read(loginId);
//        return dto == null ?
//                new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK)
//                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);

//    }

//    // http://localhost:8080/users/oauth 을 입력을 해야 json으로 토큰을 볼 수 있어
//    @GetMapping("/oauth")
//    public OAuth2AuthenticationToken oauthToken(OAuth2AuthenticationToken token) {
//        return token;
//    }


    //로그인페이지로

    @GetMapping("/login")
    public String login(HttpSession session, LoginDTO loginDTO, Model model) {
        loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        System.out.println("loginDTO = " + loginDTO);

        if(loginDTO != null) {
            System.out.println("로그인 에러..........");
            model.addAttribute("error", true);
            model.addAttribute("loginDTO", loginDTO);
            session.removeAttribute("loginDTO");
        }

        return "/festival/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "/festival/logout";
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal MemberAuthDTO memberAuthDTO){
        System.out.println(memberAuthDTO);
    }


//    @PreAuthorize("hasRole('USER') and #FestivalAuthMember.username")
    @GetMapping("/myPage")
    public String myPage(Model model, RedirectAttributes rttr, Principal principal) {

        String email = principal.getName();
        System.out.println("email = " + email);
        MyPageDTO myPage = memberService.getMyPage(email);


        model.addAttribute("myPage", myPage);
        rttr.addAttribute("email", email);

        return "/festival/myPage";
    }

    //    @PreAuthorize("hasRole('USER') and #FestivalAuthMember.username")
    @GetMapping("/myPage/myComment")
    public String myComment(Model model, RedirectAttributes rttr, Principal principal){

        String email = principal.getName();
        MyPageDTO myComment = memberService.getMyPage(email);

        List<CommentDTO> comments = myComment.getComments();

        List<BoardDTO> boardList = new ArrayList<>();


        for (CommentDTO comment : comments) {
            Long boardId = comment.getBoardId();
            BoardDTO board = boardService.getBoard(boardId);


            if(board != null) {
                boardList.add(board);
            }
        }

        model.addAttribute("myComment", myComment);
        model.addAttribute("boardList", boardList);
        rttr.addAttribute("email", email);

        return "/festival/myPage/myComment";
    }


    @GetMapping("/myPage/myBookmark")
    public String myBookmark(Model model, RedirectAttributes rttr, Principal principal){
        String email = principal.getName();
        List<BookmarkDTO> bookmarks = bookmarkService.getBookmarks(email);
        System.out.println("bookmarks = " + bookmarks);

        List<BoardDTO> boardList = new ArrayList<>();

        for (BookmarkDTO bookmark : bookmarks) {
            Long boardId = bookmark.getBoardId();
            BoardDTO board = boardService.getBoard(boardId);
            System.out.println("board = " + board);
            if(board != null) {
                boardList.add(board);
            }


            model.addAttribute("bookmarks", bookmarks);
            model.addAttribute("boardList", boardList);
            rttr.addAttribute("email", email);
        }


        return "/festival/myPage/myBookmark";
    }


    //    @PreAuthorize("hasRole('USER') and #FestivalAuthMember.username")
    @GetMapping("/myPage/myInfo")
    public String myInfo(Model model, RedirectAttributes rttr, Principal principal){

        String email = principal.getName();
        MyPageDTO myInfo = memberService.getMyPage(email);


        model.addAttribute("myInfo", myInfo);
        rttr.addAttribute("email", email);

        return "/festival/myPage/myInfo";
    }

}
