package ForMZ.Server.global.security;

import ForMZ.Server.domain.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if(authorizationHeader != null){
            setAuthPrincipal(getToken(authorizationHeader));
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

    private void setAuthPrincipal(String token) {
        AuthUser authUser = AuthUser.createAuthUser(jwtProvider.getUserId(token));
        Authentication authentication =
                    new UsernamePasswordAuthenticationToken(authUser, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
