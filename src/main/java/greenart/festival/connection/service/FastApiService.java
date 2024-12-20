package greenart.festival.connection.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FastApiService {

    private final WebClient webClient;

    public FastApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/festival/detail").build(); // FastAPI 서버의 기본 URL
    }

    // FastAPI에 요청을 보냄
    public String getFastApiResponse(String searchKeyword, String regionCode) {
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("searchKeyword", searchKeyword)
                        .queryParam("regionCode", regionCode)
                        .build())
                .retrieve().bodyToMono(String.class); // String으로 반환
        return responseMono.block(); // block 사용하여 결과를 기다림
    }
}
