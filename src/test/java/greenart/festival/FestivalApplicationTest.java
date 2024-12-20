package greenart.festival;

import greenart.festival.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FestivalApplicationTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void testCreateBoard() {

//        // 1. 서울 봄 축제
//        Board board = Board.builder()
//                .title("서울 봄 축제")
//                .content("서울에서 열리는 봄 축제에 대한 정보입니다.")
//                .location("서울 강남구")
//                .latitude(37.5665)
//                .longitude(126.90)
//                .festivalImage("seoul_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 4, 1, 10, 0))
//                .endDate(LocalDateTime.of(2024, 4, 5, 18, 0))
//                .build();
//        boardRepository.save(board);
//
//        // 2. 부산 여름 페스티벌
//        Board board2 = Board.builder()
//                .title("부산 여름 페스티벌")
//                .content("부산에서 열리는 여름 페스티벌의 안내입니다.")
//                .location("부산 해운대구")
//                .festivalImage("busan_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 7, 10, 12, 0))
//                .endDate(LocalDateTime.of(2024, 7, 15, 22, 0))
//                .latitude(35.1796)  // 부산 위도
//                .longitude(129.0756) // 부산 경도
//                .build();
//        boardRepository.save(board2);
//
//        // 3. 제주도 가을 축제
//        Board board3 = Board.builder()
//                .title("제주도 가을 축제")
//                .content("제주도에서 가을에 진행되는 축제입니다.")
//                .location("제주도")
//                .festivalImage("jeju_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 10, 5, 9, 0))
//                .endDate(LocalDateTime.of(2024, 10, 7, 18, 0))
//                .latitude(33.4996)  // 제주도 위도
//                .longitude(126.5312) // 제주도 경도
//                .build();
//        boardRepository.save(board3);
//
//        // 4. 강원도 겨울 축제
//        Board board4 = Board.builder()
//                .title("강원도 겨울 축제")
//                .content("강원도에서 열리는 겨울 축제에 대한 안내입니다.")
//                .location("강원도 평창군")
//                .festivalImage("gangwon_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 12, 20, 10, 0))
//                .endDate(LocalDateTime.of(2024, 12, 25, 18, 0))
//                .latitude(37.6397)  // 평창 위도
//                .longitude(128.1830) // 평창 경도
//                .build();
//        boardRepository.save(board4);
//
//        // 5. 대구 봄 꽃 축제
//        Board board5 = Board.builder()
//                .title("대구 봄 꽃 축제")
//                .content("대구에서 열리는 봄 꽃 축제의 내용입니다.")
//                .location("대구 중구")
//                .festivalImage("daegu_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 4, 20, 9, 0))
//                .endDate(LocalDateTime.of(2024, 4, 25, 18, 0))
//                .latitude(35.8704)  // 대구 위도
//                .longitude(128.6014) // 대구 경도
//                .build();
//        boardRepository.save(board5);
//
//        // 6. 인천 가을 문화제
//        Board board6 = Board.builder()
//                .title("인천 가을 문화제")
//                .content("인천에서 가을에 열리는 문화제입니다.")
//                .location("인천 송도")
//                .festivalImage("incheon_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 9, 15, 10, 0))
//                .endDate(LocalDateTime.of(2024, 9, 20, 18, 0))
//                .latitude(37.4563)
//                .longitude(126.7057)
//                .build();
//        boardRepository.save(board6);
//
//        // 7. 경주 전통 문화제
//        Board board7 = Board.builder()
//                .title("경주 전통 문화제")
//                .content("경주에서 전통 문화가 담긴 축제입니다.")
//                .location("경주")
//                .festivalImage("gyeongju_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 5, 1, 9, 0))
//                .endDate(LocalDateTime.of(2024, 5, 3, 18, 0))
//                .latitude(35.8563)
//                .longitude(129.2272)
//                .build();
//        boardRepository.save(board7);
//
//        // 8. 전주 한지 축제
//        Board board8 = Board.builder()
//                .title("전주 한지 축제")
//                .content("전주에서 열리는 한지 문화 축제입니다.")
//                .location("전주")
//                .festivalImage("jeonju_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 6, 1, 9, 0))
//                .endDate(LocalDateTime.of(2024, 6, 5, 18, 0))
//                .latitude(35.8174)
//                .longitude(127.1490)
//                .build();
//        boardRepository.save(board8);
//
//        // 9. 울산 해양 페스티벌
//        Board board9 = Board.builder()
//                .title("울산 해양 페스티벌")
//                .content("울산에서 여름에 열리는 해양 페스티벌입니다.")
//                .location("울산 동구")
//                .festivalImage("ulsan_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 7, 25, 10, 0))
//                .endDate(LocalDateTime.of(2024, 7, 30, 18, 0))
//                .latitude(35.5385)
//                .longitude(129.3117)
//                .build();
//        boardRepository.save(board9);
//
//        // 10. 광주 미술 축제
//        Board board10 = Board.builder()
//                .title("광주 미술 축제")
//                .content("광주에서 열리는 미술 축제입니다.")
//                .location("광주 북구")
//                .festivalImage("gwangju_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 8, 10, 9, 0))
//                .endDate(LocalDateTime.of(2024, 8, 15, 18, 0))
//                .latitude(35.1607)
//                .longitude(126.8526)
//                .build();
//        boardRepository.save(board10);
//
//        // 추가된 5개 축제
//
//        // 11. 서울 영화제
//        Board board11 = Board.builder()
//                .title("서울 영화제")
//                .content("서울에서 열리는 영화제입니다.")
//                .location("서울 종로구")
//                .festivalImage("seoul_movie_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 10, 1, 18, 0))
//                .endDate(LocalDateTime.of(2024, 10, 10, 22, 0))
//                .latitude(37.5665)
//                .longitude(126.9780)
//                .build();
//        boardRepository.save(board11);
//
//        // 12. 대전 로봇 축제
//        Board board12 = Board.builder()
//                .title("대전 로봇 축제")
//                .content("대전에서 열리는 로봇과 관련된 축제입니다.")
//                .location("대전 유성구")
//                .festivalImage("daejeon_robot_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 11, 1, 10, 0))
//                .endDate(LocalDateTime.of(2024, 11, 5, 18, 0))
//                .latitude(36.3504)
//                .longitude(127.3845)
//                .build();
//        boardRepository.save(board12);
//
//        // 13. 경기 과일 축제
//        Board board13 = Board.builder()
//                .title("경기 과일 축제")
//                .content("경기에서 열리는 과일 관련 축제입니다.")
//                .location("경기 성남시")
//                .festivalImage("gyeonggi_fruit_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 8, 5, 9, 0))
//                .endDate(LocalDateTime.of(2024, 8, 10, 18, 0))
//                .latitude(37.4138)
//                .longitude(127.1293)
//                .build();
//        boardRepository.save(board13);
//
//        // 14. 전라남도 해양 문화제
//        Board board14 = Board.builder()
//                .title("전라남도 해양 문화제")
//                .content("전라남도에서 열리는 해양과 문화가 결합된 축제입니다.")
//                .location("전라남도 여수시")
//                .festivalImage("jeonnam_sea_culture_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 9, 10, 10, 0))
//                .endDate(LocalDateTime.of(2024, 9, 15, 18, 0))
//                .latitude(34.7606)
//                .longitude(127.6622)
//                .build();
//        boardRepository.save(board14);
//
//        // 15. 세종시 스마트 기술 페스티벌
//        Board board15 = Board.builder()
//                .title("세종시 스마트 기술 페스티벌")
//                .content("세종시에서 열리는 스마트 기술을 다룬 페스티벌입니다.")
//                .location("세종시")
//                .festivalImage("sejong_tech_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 6, 25, 9, 0))
//                .endDate(LocalDateTime.of(2024, 6, 30, 18, 0))
//                .latitude(36.5361)
//                .longitude(127.2636)
//                .build();
//        boardRepository.save(board15);
//
//        // 16. 서울 핸드메이드 페스티벌
//        Board board16 = Board.builder()
//                .title("서울 핸드메이드 페스티벌")
//                .content("서울에서 열리는 핸드메이드 관련 축제입니다.")
//                .location("서울 마포구")
//                .festivalImage("seoul_handmade_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 5, 10, 10, 0))
//                .endDate(LocalDateTime.of(2024, 5, 15, 18, 0))
//                .latitude(37.5430)
//                .longitude(126.9350)
//                .build();
//        boardRepository.save(board16);
//
//        // 17. 부산 국제 영화제
//        Board board17 = Board.builder()
//                .title("부산 국제 영화제")
//                .content("부산에서 열리는 국제 영화제입니다.")
//                .location("부산 해운대구")
//                .festivalImage("busan_international_film_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 10, 4, 18, 0))
//                .endDate(LocalDateTime.of(2024, 10, 14, 22, 0))
//                .latitude(35.1675)
//                .longitude(129.1374)
//                .build();
//        boardRepository.save(board17);
//
//        // 18. 제주도 다채로운 예술 축제
//        Board board18 = Board.builder()
//                .title("제주도 다채로운 예술 축제")
//                .content("제주도에서 열리는 예술 축제입니다.")
//                .location("제주도 제주시")
//                .festivalImage("jeju_art_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 9, 10, 9, 0))
//                .endDate(LocalDateTime.of(2024, 9, 20, 18, 0))
//                .latitude(33.4890)
//                .longitude(126.4980)
//                .build();
//        boardRepository.save(board18);
//
//        // 19. 서울 세계 음악 페스티벌
//        Board board19 = Board.builder()
//                .title("서울 세계 음악 페스티벌")
//                .content("서울에서 열리는 세계 음악 축제입니다.")
//                .location("서울 송파구")
//                .festivalImage("seoul_music_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 8, 5, 18, 0))
//                .endDate(LocalDateTime.of(2024, 8, 10, 22, 0))
//                .latitude(37.5125)
//                .longitude(127.0975)
//                .build();
//        boardRepository.save(board19);
//
//        // 20. 대구 국제 마라톤 대회
//        Board board20 = Board.builder()
//                .title("대구 국제 마라톤 대회")
//                .content("대구에서 열리는 국제 마라톤 대회입니다.")
//                .location("대구 동구")
//                .festivalImage("daegu_marathon_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 3, 15, 8, 0))
//                .endDate(LocalDateTime.of(2024, 3, 15, 18, 0))
//                .latitude(35.8705)
//                .longitude(128.6009)
//                .build();
//        boardRepository.save(board20);
//
//        // 21. 광주 과학 축제
//        Board board21 = Board.builder()
//                .title("광주 과학 축제")
//                .content("광주에서 열리는 과학 관련 축제입니다.")
//                .location("광주 광산구")
//                .festivalImage("gwangju_science_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 5, 15, 9, 0))
//                .endDate(LocalDateTime.of(2024, 5, 20, 18, 0))
//                .latitude(35.2012)
//                .longitude(126.8595)
//                .build();
//        boardRepository.save(board21);
//
//        // 22. 부산 해양 스포츠 대회
//        Board board22 = Board.builder()
//                .title("부산 해양 스포츠 대회")
//                .content("부산에서 열리는 해양 스포츠 대회입니다.")
//                .location("부산 남구")
//                .festivalImage("busan_marine_sports_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 6, 25, 10, 0))
//                .endDate(LocalDateTime.of(2024, 6, 30, 18, 0))
//                .latitude(35.1470)
//                .longitude(129.0855)
//                .build();
//        boardRepository.save(board22);
//
//        // 23. 서울 전통 미술 축제
//        Board board23 = Board.builder()
//                .title("서울 전통 미술 축제")
//                .content("서울에서 열리는 전통 미술 관련 축제입니다.")
//                .location("서울 종로구")
//                .festivalImage("seoul_traditional_art_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 7, 10, 9, 0))
//                .endDate(LocalDateTime.of(2024, 7, 15, 18, 0))
//                .latitude(37.5700)
//                .longitude(126.9840)
//                .build();
//        boardRepository.save(board23);
//
//        // 24. 인천 로봇 페스티벌
//        Board board24 = Board.builder()
//                .title("인천 로봇 페스티벌")
//                .content("인천에서 열리는 로봇 관련 축제입니다.")
//                .location("인천 부평구")
//                .festivalImage("incheon_robot_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 11, 15, 10, 0))
//                .endDate(LocalDateTime.of(2024, 11, 20, 18, 0))
//                .latitude(37.4895)
//                .longitude(126.7139)
//                .build();
//        boardRepository.save(board24);
//
//        // 25. 경기도 문화 축제
//        Board board25 = Board.builder()
//                .title("경기도 문화 축제")
//                .content("경기도에서 열리는 문화 축제입니다.")
//                .location("경기 고양시")
//                .festivalImage("gyeonggi_culture_festival_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 10, 1, 9, 0))
//                .endDate(LocalDateTime.of(2024, 10, 5, 18, 0))
//                .latitude(37.6585)
//                .longitude(126.8365)
//                .build();
//        boardRepository.save(board25);
//
//        // 26. 대전 과학 전시회
//        Board board26 = Board.builder()
//                .title("대전 과학 전시회")
//                .content("대전에서 열리는 과학 관련 전시회입니다.")
//                .location("대전 서구")
//                .festivalImage("daejeon_science_exhibition_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 9, 20, 9, 0))
//                .endDate(LocalDateTime.of(2024, 9, 25, 18, 0))
//                .latitude(36.3500)
//                .longitude(127.3850)
//                .build();
//        boardRepository.save(board26);
//
//        // 27. 서울 마라톤 대회
//        Board board27 = Board.builder()
//                .title("서울 마라톤 대회")
//                .content("서울에서 열리는 마라톤 대회입니다.")
//                .location("서울 강동구")
//                .festivalImage("seoul_marathon_image.jpg")
//                .festivalFee("유료")
//                .startDate(LocalDateTime.of(2024, 11, 10, 7, 0))
//                .endDate(LocalDateTime.of(2024, 11, 10, 18, 0))
//                .latitude(37.5300)
//                .longitude(127.1200)
//                .build();
//        boardRepository.save(board27);
//
//        // 28. 전북 전통 문화 축제
//        Board board28 = Board.builder()
//                .title("전북 전통 문화 축제")
//                .content("전북에서 열리는 전통 문화 축제입니다.")
//                .location("전북 전주")
//                .festivalImage("jeonbuk_traditional_culture_festival_image.jpg")
//                .festivalFee("무료")
//                .startDate(LocalDateTime.of(2024, 8, 20, 9, 0))
//                .endDate(LocalDateTime.of(2024, 8, 25, 18, 0))
//                .latitude(35.8165)
//                .longitude(127.1485)
//                .build();
//        boardRepository.save(board28);
    }
}