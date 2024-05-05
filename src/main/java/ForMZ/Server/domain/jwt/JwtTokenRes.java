package ForMZ.Server.domain.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record JwtTokenRes(
        String accessToken,
        @JsonIgnore
        String refreshToken
) {}