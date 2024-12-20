package greenart.festival.bookmark.service;

import greenart.festival.bookmark.dto.BookmarkDTO;
import greenart.festival.bookmark.entity.Bookmark;
import greenart.festival.review.dto.ReviewDTO;
import greenart.festival.review.entity.Review;

import java.util.List;

public interface BookmarkService {

//  북마크 확인 여부
    boolean isBookmarked(String email, Long boardId);

//  북마크 리스트 조회
    List<BookmarkDTO> getBookmarks(String email);

//  북마크 설정 & 해제
    boolean bookmarking(String email, Long boardId);


    default Bookmark dtoToEntity(BookmarkDTO bookmarkDTO) {

        Bookmark bookmark = Bookmark.builder()
                .bookmarkId(bookmarkDTO.getBookmarkId())
                .email(bookmarkDTO.getEmail())
                .bookmark(bookmarkDTO.isBookmark())
                .build();

        return bookmark;
    }
}
