package greenart.festival.bookmark.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class BookmarkDTO {

    private Long bookmarkId;
    private String email;
    private boolean bookmark;
    private Long boardId;
    private Long memberId;


}
