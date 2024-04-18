package ForMZ.Server.domain.jwt.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JwtAccessExpirationException extends ApplicationException {
    public JwtAccessExpirationException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public JwtAccessExpirationException() {
        this("", HttpStatus.UNAUTHORIZED, "만료된 JWT Access 토큰입니다.");
    }
}
