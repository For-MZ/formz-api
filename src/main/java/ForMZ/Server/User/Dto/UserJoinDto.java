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

    public UserJoinDto(String loginId, String password, String email, String nickname, String loginType, String profileImageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.loginType = loginType;
        this.profileImageUrl = profileImageUrl;
    }
}
