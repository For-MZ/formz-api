package ForMZ.Server.User.Dto;

import lombok.Data;

@Data
public class UserSignUpResDto {
    private Long user_id;
    private String message;

    public UserSignUpResDto(Long user_id, String message) {
        this.user_id = user_id;
        this.message = message;
    }
}
