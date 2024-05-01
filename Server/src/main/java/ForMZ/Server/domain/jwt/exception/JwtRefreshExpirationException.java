package ForMZ.Server.domain.jwt.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JwtRefreshExpirationException extends ApplicationException {
    public JwtRefreshExpirationException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public JwtRefreshExpirationException() {
        this("", HttpStatus.FORBIDDEN, "만료된 JWT Refresh 토큰입니다.");
    }
}
