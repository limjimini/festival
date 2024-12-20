package greenart.festival.connection.controller;

import greenart.festival.connection.service.FastApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FastApiController {

    private final FastApiClient fastApiClient;

    public FastApiController(FastApiClient fastApiClient) {
        this.fastApiClient = fastApiClient;
    }

    // FastAPI에 파라미터 전달 후 추가 로직 처리
    @GetMapping("/festival/detail")
    public Mono<String> forwardRequest(@RequestParam String searchKeyword, @RequestParam String regionCode) {

        return fastApiClient.forwardParameters(searchKeyword, regionCode)
                .map(response -> {
                    System.out.println("FastApi Response = " + response);
                    return response;
                });
    }
}
