package ForMZ.Server.domain.comment.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class CommentException extends ApplicationException {
    protected CommentException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }
}
