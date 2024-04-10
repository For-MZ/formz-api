package ForMZ.Server.global.oauth.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class SocialTypeNotFoundException extends ApplicationException {
    public SocialTypeNotFoundException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }

    public SocialTypeNotFoundException() {
        this("", HttpStatus.NOT_FOUND, "지원하지 소셜 로그인입니다.");
    }
}
