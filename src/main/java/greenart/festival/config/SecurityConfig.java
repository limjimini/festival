package greenart.festival.config;

import greenart.festival.member.controller.LoginFailHandler;
import greenart.festival.member.controller.SocialLoginSuccessHandler;
import greenart.festival.member.service.FestivalAuth2UserDetailsService;
import greenart.festival.member.service.FestivalUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private SocialLoginSuccessHandler socialLoginSuccessHandler;

    @Autowired
    private FestivalUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // @Bean 자동입력창
    //  public FestivalLoginSuccessHandler festivalLoginSuccessHandler() {
    //      return new FestivalLoginSuccessHandler(passwordEncoder());}

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    //커스텀로그인페이지-로그인페이지에대한 설정(이게없으면 기본로그인폼으로 간다)
    //원하는 페이지로 갈수있도록해준다
    //설정끝내고 반드시빌드넣기
//    authorize
//                    .requestMatchers("/").permitAll()
//                    .requestMatchers("/festival/all").permitAll()
//                    .requestMatchers("/user/join").permitAll()
//                    .requestMatchers("/festival/member").hasRole("USER")
//                    .requestMatchers("/user/login").permitAll()
//                    .requestMatchers("/user/login/home").permitAll()
//                    .anyRequest().authenticated();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> {

                    authorize.anyRequest().permitAll();

//            //로그아웃상태에서 접근할수있는 페이지 -1.메인(홈)2.로그인+회원가입페이지
        }).csrf(csrf ->{csrf.disable();})
//      로그인 FORM
        .formLogin(login -> {
            login.loginPage("/festival/login")
                    .loginProcessingUrl("/festival/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler(loginFailHandler)
                    .defaultSuccessUrl("/festival/home",true);
        })
//      소셜 로그인 FORM
        .oauth2Login(oAuth2 -> {
            oAuth2.successHandler(socialLoginSuccessHandler);
        })


//      로그아웃 FORM
        .logout(logout -> {
            logout.logoutUrl("/festival/logout");
        })
        .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080/"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                }))
        .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:8000"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                }));
        return http.build();
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }


}

