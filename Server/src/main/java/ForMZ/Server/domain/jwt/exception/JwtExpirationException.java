package ForMZ.Server.domain.jwt.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JwtExpirationException extends ApplicationException {
    public JwtExpirationException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public JwtExpirationException() {
        this("", HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다.");
    }
}
