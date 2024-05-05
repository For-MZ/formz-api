package ForMZ.Server.global.cookie;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CookieUtilTest {

    @InjectMocks
    CookieUtil cookieUtil;

    @Test
    @DisplayName("Cookie 설정 - 정상")
    void setCookieTest() {
        // given
        HttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = "refresh";

        // when
        cookieUtil.setRefreshTokenInCookie(response, refreshToken);

        // then
        String setCookie = response.getHeader("Set-Cookie");
        assertThat(setCookie).isNotNull();
        assertThat(setCookie.indexOf("HttpOnly")).isNotEqualTo(-1);
        assertThat(setCookie.indexOf("Secure")).isNotEqualTo(-1);
        assertThat(setCookie.indexOf("SameSite=NONE")).isNotEqualTo(-1);
        assertThat(setCookie.indexOf("Path=/refresh")).isNotEqualTo(-1);
    }

    @Test
    @DisplayName("Cookie 삭제 - 정상")
    void deleteCookieTest() {
        // given
        HttpServletResponse response = new MockHttpServletResponse();

        // when
        cookieUtil.deleteRefreshTokenInCookie(response);

        // then
        String setCookie = response.getHeader("Set-Cookie");
        assertThat(setCookie).isNotNull();
        assertThat(setCookie.indexOf("Max-Age=0")).isNotEqualTo(-1);
    }
}