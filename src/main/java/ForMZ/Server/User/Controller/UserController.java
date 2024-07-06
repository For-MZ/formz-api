package ForMZ.Server.User.Controller;

import ForMZ.Server.Configuration.JwtTokenUtil;
import ForMZ.Server.User.Dto.*;
import ForMZ.Server.Core.Dto.JoinDto;
import ForMZ.Server.User.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    @PostMapping("/api/sign-up")
    public ResponseEntity<JoinDto> sign_up(@RequestBody UserJoinDto userJoinDto) {
        Long user_id = userService.join(userJoinDto);
        return ResponseEntity.status(201).body(new JoinDto(user_id,"성공적으로 회원가입이 완료되었습니다"));
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto userLoginDto){
        try {
            String token = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
            UserLoginResponseDto res = new UserLoginResponseDto(token, "성공적으로 로그인 하셧습니다");
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {

            return ResponseEntity.status(401).build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String accessToken = jwtTokenUtil.resolveAccessToken(request);
        userService.logout(accessToken);
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다.");
    }
    @DeleteMapping("/api/withdrawal")
    public ResponseEntity<String> withdrawal(HttpServletRequest request) throws Exception {
        String accessToken = jwtTokenUtil.resolveAccessToken(request);
        userService.deleteUser(accessToken);
        userService.logout(accessToken);
        return ResponseEntity.status(204).body("성공적으로 탈퇴 되었습니다.");
    }
    @GetMapping("/api/user")
    public ResponseEntity<UserDto> userProfile(HttpServletRequest request) throws Exception {
        try{
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            UserDto userProfile = userService.findUserProfile(accessToken);
            return ResponseEntity.status(201).body(userProfile);
        }
        catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }
    @PatchMapping("/api/userprofile")
    public ResponseEntity<UserDto> userProfileChange(HttpServletRequest request, @RequestBody ChangeProFileDto changeProFileDto){
        try{
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            UserDto userDto = userService.ChangeUserProfile(accessToken, changeProFileDto);
            return ResponseEntity.status(201).body(userDto);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
}
