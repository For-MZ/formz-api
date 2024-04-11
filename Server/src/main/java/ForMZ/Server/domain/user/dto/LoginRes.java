package ForMZ.Server.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LoginRes {
    private String accessToken;

    public static LoginRes toDto(String accessToken) {
        return LoginRes.builder().accessToken(accessToken).build();
    }
}
