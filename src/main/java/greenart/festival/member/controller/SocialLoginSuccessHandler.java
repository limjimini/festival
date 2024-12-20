package greenart.festival.member.controller;

import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Social;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberAuthDTO authMember = (MemberAuthDTO) authentication.getPrincipal();
        System.out.println("authMember = " + authMember);
        Social provider = authMember.getProvider();// 소셜로그인 인증 사용자 여부
        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        if(!provider.equals(Social.NONE) && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/festival/home");
        }
    }
}
