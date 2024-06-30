package ForMZ.Server.Configuration;



import ForMZ.Server.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.token.secret}")
    private String secretKey;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisConfig redisConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) // ui로 들어오는것
                .csrf(AbstractHttpConfigurer::disable) // 크로스 사이트
                .cors(Customizer.withDefaults()) //크로스사이트
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login","/join").permitAll()
                        .requestMatchers(HttpMethod.GET).authenticated()
                        .requestMatchers(HttpMethod.POST).authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                .addFilterBefore(new JwtFilter(jwtTokenUtil,redisConfig), UsernamePasswordAuthenticationFilter.class);
//                .exceptionHandling((exceptionConfig)->
//                        exceptionConfig.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }
}