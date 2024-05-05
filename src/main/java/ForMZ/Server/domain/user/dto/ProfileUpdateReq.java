package ForMZ.Server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileUpdateReq {
    // TODO: 추후 email, nickName, password 정규표현식으로 변경

    @Email
    private String email;
    @NotNull
    private String nickName;
    @NotNull
    private String password;

    private String profileImage;
}
