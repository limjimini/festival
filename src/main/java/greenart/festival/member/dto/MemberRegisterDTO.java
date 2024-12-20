package greenart.festival.member.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDTO {

//회원가입 양식
    @Size(max = 50, message = "이메일형태로입력")
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호:영어 숫자 조합 8자~15자 이하입력")
    private String password;

    @Size(min = 2, max = 5, message = "회원이름:한글2자~5자 이하 입력")
    private String name;

    @Size(max = 8, message = "회원생년월일 yyyyMMdd")
    private String birthDate;

    @Size(min = 11, max = 11, message = "핸드폰번호입력")
    private String phoneNumber;
}
