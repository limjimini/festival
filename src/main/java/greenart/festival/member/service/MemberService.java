package greenart.festival.member.service;

import greenart.festival.member.dto.MemberAuthDTO;
import greenart.festival.member.dto.MemberRegisterDTO;
import greenart.festival.member.dto.MyPageDTO;
import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.MemberRole;
import greenart.festival.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    Long register(MemberRegisterDTO dto);

    default Member dtoToEntity(MemberRegisterDTO dto){
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();
        member.addMemberRole(MemberRole.USER);
        return member;
    }

    default Member dtoToEntity(MemberAuthDTO dto){
        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .provider(dto.getProvider())
                .build();
        member.addMemberRole(MemberRole.USER);
        return member;
    }


//    default MemberAuthDTO entityToDTO(Member member){
//        MemberAuthDTO memberAuthDTO = MemberAuthDTO.builder()
//                .email(member.getEmail())
//                .name(member.getName())
//                .password(member.getPassword())
//                .provider(member.getProvider())
//                .build();
//        memberAuthDTO.
//
//        return memberAuthDTO;
//    }

//    public MemberRegisterDTO read(String email);


//    //241203 마이페이지 관련 추가부분
//    /* Role을 추가하는 방식 수정
//        member.addMemberRole(MemberRole.USER);
//        return member; */

    //마이페이지 비밀번호 폰번호 변경, 내가쓴 댓글 조회기능,(이메일로 로그인)

//    /* int modify(MyPageDTO dto);
//    int save(MyPageDTO dto);
//    int remove(MyPageDTO dto);
//    MyPageDTO getMyPageDTO(); */
//
    //마이페이지
    MyPageDTO getMyPage(String email);


//    void changePassword(String email, String newPassword);
//    void changePhoneNumber(String email, String newPhoneNumber);

    //댓글 게시물 조회
//    List<Long> getUserComments(String email);
}
