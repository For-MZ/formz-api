package ForMZ.Server.User.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.Configuration.JwtTokenUtil;
import ForMZ.Server.Configuration.RedisConfig;
import ForMZ.Server.User.Dto.ChangeProFileDto;
import ForMZ.Server.User.Dto.UserDto;
import ForMZ.Server.User.Dto.UserJoinDto;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisConfig redisConfig;
    private Long expireTimeMs = 100*60*60L;
    @Transactional
    public Long join(UserJoinDto userJoinDto){
        userRepository.findByUserEmail(userJoinDto.getEmail()).ifPresent(user -> {
            try {
                throw new Exception(user.getEmail() +"는 이미 있습니다.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        String EncodePassword = encoder.encode(userJoinDto.getPassword());
        User user = new User(EncodePassword,userJoinDto.getEmail(),userJoinDto.getNickname(), userJoinDto.getProfileImageUrl());
        BookMark bookMark = new BookMark();
        bookMarkRepository.save(bookMark);
        user.settingBookMark(bookMark);
        userRepository.save(user);
        return user.getId();
    }
    @Transactional
    public String login(String email, String password) throws Exception {
        User user = userRepository.findByUserEmail(email).orElseThrow(() -> new Exception("존재하지 않는 id입니다."));
        if(!encoder.matches(password,user.getPassword())){ //matchs왼쪽이 암호화 안된것 , 오른쪽이 암호화 된것
            throw new Exception("비밀번호가 틀렷습니다");
        }
        String Access_token = jwtTokenUtil.createToken(user.getEmail(),expireTimeMs);
        String reFresh_token = jwtTokenUtil.createReFreshToken(user.getEmail(),expireTimeMs);
        System.out.println(reFresh_token);
        redisConfig.redisTemplate().opsForValue().set(user.getId().toString(),reFresh_token, Duration.ofHours(3));
        return Access_token;
    }
    @Transactional
    public void logout(String AccessToken){
        String subject = jwtTokenUtil.getclaims(AccessToken).getSubject();
        redisConfig.redisTemplate().delete(subject);
    }
    @Transactional
    public void deleteUser(String accessToken) throws Exception {
        String login_email = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserEmail(login_email);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        userRepository.delete(user.get());
    }

    public UserDto findUserProfile(String accessToken) throws Exception {
        String email = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserEmail(email);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        User user1 = user.get();
        return new UserDto(user1.getId(),user1.getEmail(),user1.getNickname(),user1.getProfileImageUrl());
    }
    @Transactional
    public UserDto ChangeUserProfile(String accessToken, ChangeProFileDto changeProFileDto) throws Exception {
        String email = jwtTokenUtil.getclaims(accessToken).getSubject();
        Optional<User> user = userRepository.findByUserEmail(email);
        if(user.isEmpty()){
            throw new Exception("존재하지않는 회원입니다.");
        }
        User user1 = user.get();
        System.out.println(changeProFileDto);
        user1.changeProfile(changeProFileDto);
        userRepository.save(user1);
        return new UserDto(user1.getId(),user1.getEmail(),user1.getNickname(),user1.getProfileImageUrl());
    }
    public Optional<User> findByUserId(String email){
        return userRepository.findByUserEmail(email);
    }

}
