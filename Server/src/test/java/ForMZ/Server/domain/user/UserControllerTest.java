package ForMZ.Server.domain.user;

import ForMZ.Server.domain.jwt.JwtTokenRes;
import ForMZ.Server.domain.user.controller.UserController;
import ForMZ.Server.domain.user.service.MailSenderService;
import ForMZ.Server.domain.user.service.UserService;
import ForMZ.Server.global.config.TestConfig;
import ForMZ.Server.global.cookie.CookieUtil;
import ForMZ.Server.global.oauth.exception.AuthorizationCodeErrorException;
import ForMZ.Server.global.oauth.exception.SocialTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestConfig.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    MailSenderService mailSenderService;

    @MockBean
    CookieUtil cookieUtil;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("OAuth 로그인 / Access Token 정상 발행")
    void isSuccessLoginToOAuth() throws Exception {
        // given
        String social = "social";
        String code = "code";
        JwtTokenRes jwtTokenRes = new JwtTokenRes("엑세스 토큰", "리프레시 토큰");
        doReturn(jwtTokenRes).when(userService).loginOAuth(social, code);

        // when
        ResultActions perform = mvc.perform(post("/login").param("target", social).param("code", code));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value(jwtTokenRes.accessToken()));
    }

    @Test
    @DisplayName("OAuth 로그인 / Authorization Code Error Exception")
    void isAuthorizationCodeError() throws Exception {
        // given
        String authorizationCode = "잘못된 Authorization Code";
        AuthorizationCodeErrorException exception = new AuthorizationCodeErrorException();
        doThrow(exception).when(userService).loginOAuth("social", authorizationCode);

        // when
        ResultActions perform = mvc.perform(post("/login").param("target", "social").param("code", authorizationCode));

        // then
        perform.andExpect(status().is(exception.getHttpStatus().value()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    @DisplayName("OAuth 로그인 / Social Type Not Found Exception")
    void isNotFoundSocialType() throws Exception {
        // given
        String socialType = "지원하지 않는 소셜 로그인 타입";
        SocialTypeNotFoundException exception = new SocialTypeNotFoundException();
        doThrow(exception).when(userService).loginOAuth(anyString(), anyString());

        // when
        ResultActions perform = mvc.perform(post("/login").param("target", socialType).param("code", "code"));

        // then
        perform.andExpect(status().is(exception.getHttpStatus().value()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }
}
