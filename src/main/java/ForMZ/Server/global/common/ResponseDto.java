package ForMZ.Server.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private int statusCode;
    private String message;
    private T data;

    //응답 DTO 없는 경우
    public static <T> ResponseDto<T> create(int statusCode, String message) {
        return new ResponseDto<>(statusCode, message, null);
    }

    //응답 DTO가 있는 경우
    public static <T> ResponseDto<T> create(int statusCode, String message, T data) {
        return new ResponseDto<>(statusCode, message, data);
    }
}
