package ForMZ.Server.global.auth.jwt.service;

import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.exception.UserNotFoundException;
import ForMZ.Server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService");
        System.out.println("Login");

        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UserNotFoundException());
    }

    // 해당하는 User가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
