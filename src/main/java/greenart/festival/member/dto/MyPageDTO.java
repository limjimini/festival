package greenart.festival.member.dto;

import greenart.festival.member.entity.Social;
import greenart.festival.review.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyPageDTO {

    private String email;
    private String name;
    private String password;
//    private String phoneNumber;
    private Social provider; // 소셜 로그인 여부
    private List<CommentDTO> comments;     // 내가 쓴 댓글 정보 list



    public MyPageDTO(String email, String name, String password,
                     Social provider, List<CommentDTO> comments) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.provider = provider;
        this.comments = comments;
    }

    public Long getId() {
        return 0L;
    }

}