package ForMZ.Server.User.Dto;

import lombok.Data;

@Data
public class UserJoinDto {
    String loginId;
    String password;
    String email;
    String nickname;
    String loginType;
    String profileImageUrl;
}
