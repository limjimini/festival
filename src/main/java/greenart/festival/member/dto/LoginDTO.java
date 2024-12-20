package greenart.festival.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class LoginDTO {
    private String email;
    private String errorMessage;
}
