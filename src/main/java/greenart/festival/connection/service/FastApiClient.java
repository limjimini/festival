package greenart.festival.connection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FastApiClient {

    private final WebClient webClient;

    public FastApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/").build();
    }

    public Mono<String> forwardParameters(String searchKeyword, String regionCode){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/")
                        .queryParam("search_keyword", searchKeyword)
                        .queryParam("region_code", regionCode)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

    }
}
