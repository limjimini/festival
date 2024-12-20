package greenart.festival.member.service;

import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import greenart.festival.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FestivalUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("FestivalUserDetailsService.loadUserByUsername"+username);


        Optional<Member> result = memberRepository.findByEmail(username, Social.NONE);
        System.out.println("FestivalUserDetailsService.loadUserByUsername"+result);

        if(!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }
            Member member = result.get();

            System.out.println("member = " + member);

            MemberAuthDTO memberAuthDTO = new MemberAuthDTO(
                    member.getEmail(),
                    member.getName(),
                    member.getPassword(),
                    member.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .collect(Collectors.toSet()),
                    member.getProvider()
            );

        System.out.println("FestivalUserDetailsService.loadUserByUsername" + username);
        return memberAuthDTO;
    }



}
