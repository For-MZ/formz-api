package ForMZ.Server.domain.jwt;

public record JwtToken (
        String accessToken,
        String refreshToken
) {}