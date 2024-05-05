package ForMZ.Server.domain.user.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum UserExceptionList {
    USER_NOT_FOUND("US01", HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");

    private final String statusCode;
    private final HttpStatus httpStatus;
    private final String message;

}
