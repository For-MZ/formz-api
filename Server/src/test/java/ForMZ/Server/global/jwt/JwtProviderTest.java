package ForMZ.Server.global.jwt;

import ForMZ.Server.global.jwt.exception.JwtExpirationException;
import ForMZ.Server.global.jwt.exception.JwtModulationException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.Key;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class JwtProviderTest {

    @InjectMocks
    JwtProvider jwtProvider;

    @Mock
    JwtProperty jwtProperty;

    long userId = 1;
    long expiration = 1000;
    String baseKey = "test-test-test-test-test";
    byte[] encode = Base64.getEncoder().encode(baseKey.getBytes());
    Key key = Keys.hmacShaKeyFor(encode);

    @Test
    @DisplayName("토큰 검증 / 정상")
    void verifySuccessToken() {
        // given
        doReturn(key).when(jwtProperty).getKey();
        doReturn(expiration).when(jwtProperty).getAccessExpiration();

        String accessToken = new JwtFactory(jwtProperty).createAccessToken(userId);

        // when
        long getUserId = jwtProvider.getUserId(accessToken);

        // then
        assertThat(getUserId).isEqualTo(userId);
    }

    @Test
    @DisplayName("토큰 검증 / 변조")
    void verifyFailedModulation() {
        // given
        byte[] encode = Base64.getEncoder().encode((baseKey + "변조").getBytes());
        Key key = Keys.hmacShaKeyFor(encode);
        doReturn(key).when(jwtProperty).getKey();

        String accessToken = new JwtFactory(jwtProperty).createAccessToken(userId);

        // when
        doReturn(this.key).when(jwtProperty).getKey();
        JwtModulationException exception = assertThrows(JwtModulationException.class, () -> jwtProvider.getUserId(accessToken));

        // then
        assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("토큰 검증 / 만료")
    void verifyFailedExpiration() {
        // given
        long expiration = 0;
        doReturn(key).when(jwtProperty).getKey();
        doReturn(expiration).when(jwtProperty).getAccessExpiration();

        String accessToken = new JwtFactory(jwtProperty).createAccessToken(userId);

        // when
        JwtExpirationException exception = assertThrows(JwtExpirationException.class, () -> jwtProvider.getUserId(accessToken));

        // then
        assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
