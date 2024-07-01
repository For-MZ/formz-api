package ForMZ.Server.User.Dto;

import lombok.Data;

@Data
public class UserJoinDto {
    String password;
    String email;
    String nickname;
    String profileImageUrl;

    public UserJoinDto( String password, String email, String nickname, String profileImageUrl) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
