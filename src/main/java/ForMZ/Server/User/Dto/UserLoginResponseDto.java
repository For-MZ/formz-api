package ForMZ.Server.User.Dto;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private String AccessToken;
    private String message;

    public UserLoginResponseDto(String token, String message) {
        this.AccessToken=token;
        this.message=message;

    }
}
