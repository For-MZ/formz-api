package ForMZ.Server.global.exception;

import ForMZ.Server.global.common.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationEx(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ExceptionResponse(e.getStatusCode(), e.getMessage()));
    }
}
