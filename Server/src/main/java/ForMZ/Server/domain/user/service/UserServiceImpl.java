package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.user.dto.LoginRes;
import ForMZ.Server.domain.user.dto.UserReq;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.exception.UserNotFoundException;
import ForMZ.Server.domain.user.mapper.UserMapper;
import ForMZ.Server.domain.user.repository.UserRepository;
import ForMZ.Server.domain.jwt.JwtFactory;
import ForMZ.Server.global.oauth.OAuthRequestUtil;
import ForMZ.Server.global.oauth.dto.OAuthUserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final OAuthRequestUtil oAuthRequestUtil;
    private final JwtFactory jwtFactory;

    /**
     * UserId를 통한 유저 조회
     */
    @Override
    public User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }

    /**
     * 현재 접속 중인 유저 정보 가져오기
     */
    public User getCurrentUser() {
        Long userId = 0L;   // TODO : 현재 유저 ID 가져오기
        return getUser(userId);
    }

//    @Override
//    @Transactional
//    public JwtToken Login(LoginReq loginReq) {
//
//        final String email = loginReq.getEmail();
//        final String password = loginReq.getPassword();
//
//        // email + password 기반으로 Authentication 객체 생성
//        // 이 때, authentication 은 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//
//        // 실제 검증. authenticate() 메서드를 통해 요청된 User에 대한 검증 진행
//        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 인증 정보를 기반으로 JWT 토큰 생성
//        return jwtTokenProvider.generateToken(authentication);
//    }

    /**
     * 회원가입
     */
    public void createUser(UserReq userReq){
        User user = mapper.userReqToUser(userReq);
        userRepository.save(user);
    }

    /**
     * 이메일로 유저 찾기
     */
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

//    @Override
//    public LoginRes Login(LoginReq loginReq) {
//        return null;
//    }

    @Override
    @Transactional
    public LoginRes loginOAuth(String target, String code) {
        OAuthUserInfo oAuthUserInfo = oAuthRequestUtil.getOAuthUserInfo(target, code);

        User user = userRepository.findBySignTypeAndSocialId(oAuthUserInfo.getSocialType(), oAuthUserInfo.getSocialId())
                .map(existUser -> {
                    existUser.updateOAuthInfo(oAuthUserInfo);
                    return existUser;
                })
                .orElse(userRepository.save(User.toEntity(oAuthUserInfo)));

        // TODO : JWT Service 변경
        String accessToken = jwtFactory.createAccessToken(user.getId());

        return LoginRes.toDto(accessToken);
    }
}
