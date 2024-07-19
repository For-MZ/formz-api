package ForMZ.Server.Configuration;

import ForMZ.Server.Post.Repository.PostRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
public class ConfigurationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisConfig redisConfig;
    private final Long expireTimeMs = 1000*60*60L;
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request){
        String AccessToken = jwtTokenUtil.resolveAccessToken(request);
        String no1 = jwtTokenUtil.getclaims(AccessToken).getSubject();
        Optional<String> refreshToken = Optional.ofNullable(redisConfig.redisTemplate().opsForValue().get(no1));
        if(refreshToken.isEmpty()){
            return ResponseEntity.status(404).body("리프레시 토큰이 만료되었습니다");
        }
        Claims getclaims = jwtTokenUtil.getclaims(refreshToken.get());
        String id = getclaims.getSubject();
        String new_token = jwtTokenUtil.createToken(id, expireTimeMs);
        return ResponseEntity.status(200).body(new_token);
    }
}
