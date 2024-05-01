package ForMZ.Server.domain.jwt;

import ForMZ.Server.domain.jwt.exception.NotFoundRefreshTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;
  
    @Mock
    JwtFactory jwtFactory;

    @Mock
    JwtProperty jwtProperty;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    JwtRepository jwtRepository;

    @Test
    @DisplayName("User Id 기반 JWT 생성 및 저장")
    void createJwt() {
        // given
        String accessToken = "엑세스 토큰";
        String refreshToken = "리프레시 토큰";
        RefreshToken entity = RefreshToken.toEntity(refreshToken, 1L);

        doReturn(refreshToken).when(jwtFactory).createRefreshToken();
        doReturn(entity).when(jwtRepository).save(any());
        doReturn(accessToken).when(jwtFactory).createAccessToken(anyLong());

        // when
        JwtToken jwtToken = jwtService.createJwtToken(1L);

        // then
        assertThat(jwtToken.accessToken()).isEqualTo(accessToken);
        assertThat(jwtToken.refreshToken()).isEqualTo(refreshToken);
    }
    
    @Test
    @DisplayName("새로운 Refresh Token 발급")
    void giveNewRefreshToken() {
        // given
        String accessToken = "엑세스 토큰";
        String refreshToken = "리프레시 토큰";

        doReturn(refreshToken).when(jwtFactory).createRefreshToken();
        doReturn(accessToken).when(jwtFactory).createAccessToken(anyLong());
        doReturn(1L).when(jwtProperty).getRefreshExpiration();
        doReturn(Optional.empty()).when(jwtRepository).findByUserId(anyLong());
        doReturn(RefreshToken.builder().value(refreshToken).build()).when(jwtRepository).save(any());

        // when
        JwtTokenRes jwtTokenRes = jwtService.createJwtToken(anyLong());

        // then
        assertThat(jwtTokenRes.accessToken()).isEqualTo(accessToken);
        assertThat(jwtTokenRes.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("Access Token 재발급 - 정상")
    void testRefreshAccessToken() {
        // given
        String accessToken = "액세스 토큰";
        RefreshToken refreshToken = RefreshToken.builder().userId(1L).build();

        doNothing().when(jwtProvider).verifyRefreshToken(anyString());
        doReturn(Optional.of(refreshToken)).when(jwtRepository).findById(anyString());
        doReturn(accessToken).when(jwtFactory).createAccessToken(refreshToken.getUserId());

        // when
        JwtTokenRes jwtTokenRes = jwtService.reIssueAccessToken(anyString());

        // then
        assertThat(jwtTokenRes.accessToken()).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("Redis에 해당하는 Refresh Token이 없는 경우")
    void notFoundRefreshTokenInRedis() {
        // given
        NotFoundRefreshTokenException exception = new NotFoundRefreshTokenException();

        doNothing().when(jwtProvider).verifyRefreshToken(anyString());
        doThrow(exception).when(jwtRepository).findById(anyString());

        // when
        NotFoundRefreshTokenException result = assertThrows(NotFoundRefreshTokenException.class, () -> jwtService.reIssueAccessToken(anyString()));

        // then
        assertThat(result.getStatusCode()).isEqualTo(exception.getStatusCode());
        assertThat(result.getHttpStatus()).isEqualTo(exception.getHttpStatus());
        assertThat(result.getMessage()).isEqualTo(exception.getMessage());
    }
}
