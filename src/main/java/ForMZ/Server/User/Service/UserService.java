package ForMZ.Server.User.Service;

import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.Configuration.EncoderConfig;
import ForMZ.Server.Core.JwtTokenUtil;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.User.Dto.ChangeProFileDto;
import ForMZ.Server.User.Dto.UserDto;
import ForMZ.Server.User.Dto.UserJoinDto;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;
    private Long expireTimeMs = 100*60*60L;
    @Transactional
    public void join(UserJoinDto userJoinDto){
        userRepository.findByUserId(userJoinDto.getLoginId()).ifPresent(user -> {
            try {
                throw new Exception(user.getLoginId() +"는 이미 있습니다.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        String EncodePassword = encoder.encode(userJoinDto.getPassword());
        User user = new User(userJoinDto.getLoginId(), EncodePassword, userJoinDto.getNickname(),userJoinDto.getNickname()
        ,userJoinDto.getLoginType(), userJoinDto.getProfileImageUrl());
        userRepository.save(user);
    }
    public List<String> login(String id, String password) throws Exception {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new Exception("존재하지 않는 id입니다."));
        if(!encoder.matches(password,user.getPassword())){ //matchs왼쪽이 암호화 안된것 , 오른쪽이 암호화 된것
            throw new Exception("비밀번호가 틀렷습니다");
        }
        String Access_token = jwtTokenUtil.createToken(user.getLoginId(),expireTimeMs);
        String reFresh_token = jwtTokenUtil.createReFreshToken(user.getLoginId(),expireTimeMs);
        List<String> token = new ArrayList<>();
        token.add(Access_token);
        token.add(reFresh_token);
        return token;
    }

    public void deleteUser(String accessToken) throws Exception {
        String login_id = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserId(login_id);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        userRepository.delete(user.get());
    }

    public UserDto findUserProfile(String accessToken) throws Exception {
        String login_id = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserId(login_id);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        User user1 = user.get();
        return new UserDto(user1.getLoginId(),user1.getEmail(),user1.getNickname(),user1.getProfileImageUrl());
    }
    public UserDto ChangeUserProfile(String accessToken, ChangeProFileDto changeProFileDto) throws Exception {
        String login_id = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserId(login_id);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        User user1 = user.get();
        user1.changeProfile(changeProFileDto);
        userRepository.save(user1);
        return new UserDto(user1.getLoginId(),user1.getEmail(),user1.getNickname(),user1.getProfileImageUrl());
    }
}
