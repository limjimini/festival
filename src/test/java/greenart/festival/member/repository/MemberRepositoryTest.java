package greenart.festival.member.repository;

import greenart.festival.member.entity.Member;
import greenart.festival.member.entity.Social;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void test(){


        Optional<Member> member1 = memberRepository.findByEmail("mpi9904@gamil.com", Social.NONE);
        Optional<Member> member2 = memberRepository.findByEmail("mpi9904@naver.com", Social.NAVER);
        System.out.println("member = " + member1);
        System.out.println("member = " + member2);
    }
}
