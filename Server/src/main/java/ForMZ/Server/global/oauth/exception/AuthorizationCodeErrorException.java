package ForMZ.Server.global.oauth.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AuthorizationCodeErrorException extends ApplicationException {
    public AuthorizationCodeErrorException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public AuthorizationCodeErrorException() {
        this("", HttpStatus.BAD_REQUEST, "올바르지 않은 Authorization Code 입니다.");
    }
}
