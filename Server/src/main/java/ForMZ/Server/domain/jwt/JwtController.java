package ForMZ.Server.domain.jwt;

import ForMZ.Server.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<?> reIssueAccessToken(@CookieValue("Refresh") String refreshToken) {
        JwtTokenRes jwtTokenRes = jwtService.reIssueAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.create(HttpStatus.OK.value(), "", jwtTokenRes));
    }
}
