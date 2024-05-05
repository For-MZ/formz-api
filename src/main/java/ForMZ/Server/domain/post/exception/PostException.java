package ForMZ.Server.domain.post.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class PostException extends ApplicationException {
    protected PostException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }
}
