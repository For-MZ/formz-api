package ForMZ.Server.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserConstant {
    @Getter
    @RequiredArgsConstructor
    public enum AuthResponseMessage{
        CREATE_USER_SUCCESS("성공적으로 회원가입이 되었습니다."),
        LOGIN_USER_SUCCESS("성공적으로 로그인 하였습니다."),
        MAIL_SEND_SUCCESS("성공적으로 인증메일을 보냈습니다.");
        private final String message;
    }
}
