package ForMZ.Server.domain.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    @Mock
    JwtFactory jwtFactory;

    @Mock
    JwtProperty jwtProperty;

    @Mock
    JwtRepository jwtRepository;

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
        JwtToken jwtToken = jwtService.createJwtToken(anyLong());

        // then
        assertThat(jwtToken.accessToken()).isEqualTo(accessToken);
        assertThat(jwtToken.refreshToken()).isEqualTo(refreshToken);
    }
}
