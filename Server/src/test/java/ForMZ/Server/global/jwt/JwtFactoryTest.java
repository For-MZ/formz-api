package ForMZ.Server.global.jwt;

import ForMZ.Server.domain.jwt.JwtFactory;
import ForMZ.Server.domain.jwt.JwtProperty;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class JwtFactoryTest {

    @InjectMocks
    JwtFactory jwtFactory;

    @Mock
    JwtProperty jwtProperty;

    long expiration = 1L;
    String baseKey = "test-test-test-test-test";
    byte[] encode = Base64.getEncoder().encode(baseKey.getBytes());
    Key key = Keys.hmacShaKeyFor(encode);

    @Test
    @DisplayName("Access Token 생성 / 정상")
    void createSuccessAccessToken() {
        // given
        long userId = 1L;
        doReturn(expiration).when(jwtProperty).getAccessExpiration();
        doReturn(key).when(jwtProperty).getKey();

        // when
        String accessToken = jwtFactory.createAccessToken(userId);

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("Refresh Token 생성 / 정상")
    void createSuccessRefreshToken() {
        // given
        doReturn(expiration).when(jwtProperty).getRefreshExpiration();
        doReturn(key).when(jwtProperty).getKey();

        // when
        String refreshToken = jwtFactory.createRefreshToken();

        // then
        assertThat(refreshToken).isNotNull();
    }
}
