package greenart.festival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FestivalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestivalApplication.class, args);
    }

}
