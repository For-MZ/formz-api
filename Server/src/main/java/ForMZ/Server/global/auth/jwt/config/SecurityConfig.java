package ForMZ.Server.global.auth.jwt.config;

import ForMZ.Server.global.auth.jwt.filter.JwtAuthenticationFilter;
import ForMZ.Server.global.auth.jwt.tokenizer.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
        return security
                // CSRF 보안 비활성화 (Session 기반 인증이 아니면(REST API) 굳이 필요없다고 한다)
                .csrf(AbstractHttpConfigurer::disable)
                // TODO: 요청에 따른 허가 범위 설정 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/login")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/my-page")
                        ).hasRole("USER")
                        .anyRequest().authenticated())

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // DelegatingPasswordEncoder : Spring Security의 권장 알고리즘을 사용하여 비밀번호를 인코딩
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
