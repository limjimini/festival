package greenart.festival.member.service;

import greenart.festival.member.dto.MemberRegisterDTO;
import greenart.festival.member.dto.MyPageDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import greenart.festival.member.repository.MemberRepository;
import greenart.festival.review.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public Long register(MemberRegisterDTO dto) {
        String password = dto.getPassword();

        dto.setPassword(passwordEncoder.encode(password));
        Member member = dtoToEntity(dto);
        memberRepository.save(member);


        return member.getId();
    }


//    @Override
//    public MemberRegisterDTO read(String loginId) {
//        return null;
//    }

//    @Override
//    public MemberRegisterDTO read(String email) {
//
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        MemberRegisterDTO memberRegisterDTO = new MemberRegisterDTO(
//                member.getEmail(),
//                member.getPassword(),
//                member.getName()
//        );
//
//
//        return null;
//    }

    @Override
    public MyPageDTO getMyPage(String email) {
        Member member = memberRepository.findByEmail(email, Social.NONE)
                .orElseThrow(() -> new RuntimeException("Member not found"));




        List<CommentDTO> comments = memberRepository.findCommentIdsByEmail(member.getEmail());


        return new MyPageDTO(
                member.getEmail(),
                member.getName(),
                member.getPassword(),
                member.getProvider(),
                comments
        );
    }
//
//    @Override
//    public List<Long> getUserComments(String email) {
//        return List.of();
//    }


}
