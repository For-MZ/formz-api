package ForMZ.Server.global.exception;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException{
    private final String statusCode;
    private final HttpStatus httpStatus;

    public ApplicationException(String statusCode, HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
