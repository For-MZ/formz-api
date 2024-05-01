package ForMZ.Server.domain.jwt;

import ForMZ.Server.domain.jwt.exception.JwtAccessExpirationException;
import ForMZ.Server.domain.jwt.exception.JwtModulationException;
import ForMZ.Server.domain.jwt.exception.JwtRefreshExpirationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperty jwtProperty;

    public long getUserId(String token) {
        return Long.parseLong(getClaims(token, false).getSubject());
    }

    public void verifyRefreshToken(String token) {
        getClaims(token, true);
    }

    private Claims getClaims(String token, boolean refresh) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperty.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException | MissingClaimException ex) {
            throw new JwtModulationException();
        } catch (ExpiredJwtException ex) {
            throw refresh ? new JwtRefreshExpirationException() : new JwtAccessExpirationException();
        }
    }
}
