package ForMZ.Server.domain.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    @Mock
    JwtRepository jwtRepository;

    @Mock
    JwtFactory jwtFactory;

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
}
