package greenart.festival.member.controller;

import greenart.festival.member.dto.LoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage="아이디 또는 비밀번호가 틀렸습니다.";
        } else{

            errorMessage="알 수없는 오류입니다. 관리자에게 문의하십시오.";

        }
        HttpSession session = request.getSession();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(request.getParameter("email"));
        loginDTO.setErrorMessage(errorMessage);

        session.setAttribute("loginDTO", loginDTO);
        setDefaultFailureUrl("/festival/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
