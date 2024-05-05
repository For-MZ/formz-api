package ForMZ.Server.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private String statusCode;
    private String message;
    private LocalDateTime timeStamp;

    public ExceptionResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = LocalDateTime.now().withNano(0);
    }
}
