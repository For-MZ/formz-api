package ForMZ.Server.global.security;

import ForMZ.Server.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTest {

    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @Mock
    JwtProvider jwtProvider;

    @BeforeEach
    void emptyPrincipal() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    @DisplayName("SecurityContextHolder 의 Principal 정상 주입")
    void isExistPrincipal() throws ServletException, IOException {
        // given
        long userId = 1L;
        String token = "테스트 토큰";

        HttpServletRequest request = mock(HttpServletRequest.class);

        doReturn("Bearer " + token).when(request).getHeader("Authorization");
        doReturn(userId).when(jwtProvider).getUserId(token);

        // when
        authorizationFilter.doFilterInternal(request, mock(HttpServletResponse.class), mock(FilterChain.class));

        // then
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertThat(authUser.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("토큰이 없는 경우 빈 Principal")
    void isEmptyPrincipal() throws ServletException, IOException {
        // when
        authorizationFilter.doFilterInternal(mock(HttpServletRequest.class), mock(HttpServletResponse.class), mock(FilterChain.class));

        // then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();
    }
}
