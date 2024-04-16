package ForMZ.Server.domain.user.controller;

import ForMZ.Server.domain.jwt.JwtToken;
import ForMZ.Server.domain.user.dto.MailReq;
import ForMZ.Server.domain.user.dto.MailRes;
import ForMZ.Server.domain.user.service.MailSenderService;
import ForMZ.Server.domain.user.service.UserService;
import ForMZ.Server.global.common.ResponseDto;
import ForMZ.Server.domain.user.dto.UserReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ForMZ.Server.domain.user.constant.UserConstant.AuthResponseMessage.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailSenderService mailSenderService;

    /**
     * 일반 로그인
     */
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody LoginReq loginReq){
//        JwtToken jwtToken = userService.Login(loginReq);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.create(HttpStatus.CREATED.value(), LOGIN_USER_SUCCESS.getMessage(), jwtToken));
//    }

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserReq userReq){
        userService.createUser(userReq);
        long userId = userService.getUserByEmail(userReq.getEmail()).getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.create(HttpStatus.OK.value(), CREATE_USER_SUCCESS.getMessage(), userId));
    }

    /**
     * 이메일 인증 요청
     */
    @PostMapping("/email")
    public ResponseEntity mailSend(@RequestBody MailReq mailReq){
        MailRes res = mailSenderService.joinEmail(mailReq.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), MAIL_SEND_SUCCESS.getMessage(), res));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginOAuth(@RequestParam("target") String target, @RequestParam("code") String code) {
        JwtToken jwtToken = userService.loginOAuth(target, code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.create(HttpStatus.OK.value(), LOGIN_USER_SUCCESS.getMessage(), jwtToken));
    }
}
