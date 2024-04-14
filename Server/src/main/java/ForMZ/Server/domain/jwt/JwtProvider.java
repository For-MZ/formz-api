package ForMZ.Server.domain.jwt;

import ForMZ.Server.domain.jwt.exception.JwtExpirationException;
import ForMZ.Server.domain.jwt.exception.JwtModulationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperty jwtProperty;

    public long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperty.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException | MissingClaimException ex) {
            throw new JwtModulationException();
        } catch (ExpiredJwtException ex) {
            throw new JwtExpirationException();
        }
    }
}
