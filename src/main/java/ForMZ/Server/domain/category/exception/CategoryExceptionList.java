package ForMZ.Server.domain.category.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryExceptionList {
    CATEGORY_NOT_FOUND("CT01", HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");

    private final String statusCode;
    private final HttpStatus httpStatus;
    private final String message;

}
