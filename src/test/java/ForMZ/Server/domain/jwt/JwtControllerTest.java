package ForMZ.Server.domain.jwt;

import ForMZ.Server.global.config.TestConfig;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JwtController.class)
@Import(TestConfig.class)
public class JwtControllerTest {

    @MockBean
    JwtService jwtService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("AccessToken 재발급 - 성공")
    void successReIssueAccessToken() throws Exception {
        // given
        JwtTokenRes jwtTokenRes = new JwtTokenRes("재발급 액세스 토큰", "리프레시 토큰");
        Cookie cookie = new Cookie("Refresh", jwtTokenRes.refreshToken());

        doReturn(jwtTokenRes).when(jwtService).reIssueAccessToken(cookie.getValue());

        // when
        ResultActions perform = mvc.perform(post("/refresh").cookie(cookie));

        // then
        verify(jwtService, times(1)).reIssueAccessToken(cookie.getValue());
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value(jwtTokenRes.accessToken()));
    }
}
