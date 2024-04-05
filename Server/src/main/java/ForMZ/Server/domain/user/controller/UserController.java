package ForMZ.Server.domain.user.controller;

import ForMZ.Server.domain.user.service.UserService;
import ForMZ.Server.global.auth.jwt.dto.JwtToken;
import ForMZ.Server.global.auth.jwt.dto.LoginReq;
import ForMZ.Server.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq loginReq){
        JwtToken jwtToken = userService.Login(loginReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.create(HttpStatus.CREATED.value(), "로그인에 성공하였습니다", jwtToken));
    }

    @RequestMapping("/login/oauth")
    public ResponseEntity loginOauth(){

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
