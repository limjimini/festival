package greenart.festival.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");

    }



//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().configurationSource(request -> {
//            var cors = new CorsConfiguration();
//            cors.setAllowedOrigins(List.of("http://127.0.0.1:8000"));
//            cors.setAllowedMethods(List.of("GET", "POST"));
//            cors.setAllowedHeaders(List.of("*"));
//            return cors;
//        });
//    }
}
