package ForMZ.Server.global.auth.jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReq {
    private String email;
    private String password;
}