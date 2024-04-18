package ForMZ.Server.global.cookie;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private static final String SET_COOKIE = "Set-Cookie";

    private static final String REFRESH_HEADER = "Refresh";

    @Value("${jwt.refresh.expiration}")
    private long maxAge;

    public void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        setCookie(response, maxAge / 1000, refreshToken);
    }

    public void deleteRefreshTokenInCookie(HttpServletResponse response) {
        setCookie(response, 0, null);
    }

    private void setCookie(HttpServletResponse response, long maxAge, String value) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_HEADER, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("NONE")
                .maxAge(maxAge)
                .path("/refresh")
                .build();

        response.addHeader(SET_COOKIE, cookie.toString());
    }
}
