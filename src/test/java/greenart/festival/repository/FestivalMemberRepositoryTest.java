package greenart.festival.repository;

import greenart.festival.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import greenart.festival.member.entity.MemberRole;

import greenart.festival.member.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class FestivalMemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

}