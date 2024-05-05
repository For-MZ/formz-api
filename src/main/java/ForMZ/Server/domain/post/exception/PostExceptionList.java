package ForMZ.Server.domain.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostExceptionList {
    POST_NOT_FOUND("PO01", HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    UNAUTHORIZED_POST_ACCESS("PO02", HttpStatus.UNAUTHORIZED, "게시물 접근 권한이 없습니다."),
    INVALID_SORT_PARAM("PO03", HttpStatus.BAD_REQUEST, "올바르지 않은 정렬 기준입니다."),
    POST_BY_SEARCH_NOT_FOUND("PO04", HttpStatus.NOT_FOUND, "일치하는 검색 결과가 없습니다.");

    private final String statusCode;
    private final HttpStatus httpStatus;
    private final String message;

}
