package ForMZ.Server.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Getter
@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.token.secret}")
    private String secretKey;

    public Boolean isExpired(String token){
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);
        return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(accessSecret)).parseClaimsJws(token).getBody()
                .getExpiration().before(new Date());
    }
    public String createToken(String email, long expireTimeMs){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles","user");
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .setClaims(claims)//정보를 넣어줌 claims가 포함된 jwt빌더를 반환
                .setIssuedAt(new Date(System.currentTimeMillis()))//시작시간
                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))//만료시간
                .signWith(Keys.hmacShaKeyFor(accessSecret))
                .compact();

    }
    public Claims getclaims(String token){
        byte[] parser_key = secretKey.getBytes(StandardCharsets.UTF_8);
        try {
            Claims body = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(parser_key)).parseClaimsJws(token)
                    .getBody();
            return body;
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }

    }

    public String createReFreshToken(String email,Long expireTimeMs) {
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);
        Claims claims = Jwts.claims().setSubject(email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))//시작시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs * 100))//만료시간
                .signWith(Keys.hmacShaKeyFor(accessSecret))
                .compact();
    }
    public String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("authorization") != null )
            return request.getHeader("authorization").substring(7);
        return null;
    }
    public boolean validateToken(String jwtToken) {
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage());
            return false;
        }
    }
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Object no = this.getclaims(token).getSubject();
        return new UsernamePasswordAuthenticationToken(no, "", List.of(new SimpleGrantedAuthority("user")));
    }
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "Bearer "+ accessToken);
    }

}
