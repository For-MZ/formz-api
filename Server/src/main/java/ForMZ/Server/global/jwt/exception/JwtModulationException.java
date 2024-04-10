package ForMZ.Server.global.jwt.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JwtModulationException extends ApplicationException {
    public JwtModulationException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public JwtModulationException() {
        this("", HttpStatus.FORBIDDEN, "변조된 JWT 토큰입니다.");
    }
}
