package ForMZ.Server.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFactory {

    private final JwtProperty jwtProperty;

    private final Map<String, Object> header = Map.of("typ", "JWT", "alg", "HS256");

    public String createAccessToken(long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeader(header)
                .setClaims(Jwts.claims().setSubject(String.valueOf(userId)))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperty.getAccessExpiration()))
                .signWith(jwtProperty.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setHeader(header)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperty.getRefreshExpiration()))
                .signWith(jwtProperty.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
