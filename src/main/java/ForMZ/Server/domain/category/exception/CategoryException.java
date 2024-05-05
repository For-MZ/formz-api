package ForMZ.Server.domain.category.exception;

import ForMZ.Server.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class CategoryException extends ApplicationException {
    protected CategoryException(String statusCode, HttpStatus httpStatus, String message) {
        super(statusCode, httpStatus, message);
    }
}
