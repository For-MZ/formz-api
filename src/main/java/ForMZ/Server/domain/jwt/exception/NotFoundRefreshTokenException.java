package ForMZ.Server.domain.jwt.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundRefreshTokenException extends ApplicationException {
    public NotFoundRefreshTokenException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public NotFoundRefreshTokenException() {
        this("", HttpStatus.NOT_FOUND, "존재하지 않는 Refresh Token 입니다.");
    }
}
