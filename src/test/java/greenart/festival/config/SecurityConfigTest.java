package greenart.festival.config;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityConfigTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void passwordEncoderTest() {
        String password ="1111";
        String enPwd = passwordEncoder.encode(password);
        System.out.println("enPwd = " + enPwd);
        boolean matches = passwordEncoder.matches(password, enPwd);
        System.out.println("matches = " + matches);
    }
}