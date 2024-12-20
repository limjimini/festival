package greenart.festival.member.service;

import greenart.festival.member.OAuth.GoogleUserDetails;
import greenart.festival.member.OAuth.KakaoUserDetails;
import greenart.festival.member.OAuth.NaverUserDetails;
import greenart.festival.member.OAuth.OAuth2UserInfo;
import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import greenart.festival.member.entity.MemberRole;
import greenart.festival.member.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FestivalAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User =  super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;


        if(provider.equals("google")) {
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }else if(provider.equals("kakao")){
            log.info("카카오 로그인");
            oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
            
        }else if(provider.equals("naver")){
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        }else{
            throw new OAuth2AuthenticationException("저희는 구글, 카카오, 네이버만 지원합니다.");
        }


        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        Social socialProvider = oAuth2UserInfo.getProvider();


        Member member = saveSocialMember(email, name, socialProvider);


        MemberAuthDTO memberAuthDTO = new MemberAuthDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getRoles().stream().map(
                                role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList()),
                oAuth2UserInfo.getProvider(),
                oAuth2User.getAttributes()
        );


        return memberAuthDTO;
    }

    private Member saveSocialMember(String email, String name, Social provider) {
        Optional<Member> result = memberRepository.findByEmail(email, provider);

        if(result.isPresent()) {
            return result.get();
        }

        Member member =  Member.builder().email(email)
                .name(name)
                .password(passwordEncoder.encode("1111"))
                .provider(provider)
                .build();

        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);

        return member;
    }


//    private Member handleSocialLogin(String email){
//
//        for(Social provider : Social.values()){
//            Optional<Member> result = memberRepository.findByEmail(email, provider);
//            if(result.isPresent()) {
//                return result.get();
//            }
//        }
//
//        return saveSocialMember(email, Social.NONE);
//    }
}
