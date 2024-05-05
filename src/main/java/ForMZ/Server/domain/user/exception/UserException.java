package ForMZ.Server.domain.user.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class UserException extends ApplicationException {
    protected UserException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }
}
