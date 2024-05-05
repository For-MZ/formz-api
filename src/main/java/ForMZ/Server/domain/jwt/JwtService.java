package ForMZ.Server.domain.jwt;

import ForMZ.Server.domain.jwt.exception.NotFoundRefreshTokenException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtFactory jwtFactory;
    private final JwtProvider jwtProvider;
    private final JwtProperty jwtProperty;
    private final JwtRepository jwtRepository;

    @Transactional
    public JwtTokenRes createJwtToken(long userId) {
        RefreshToken refreshToken = jwtRepository.findByUserId(userId)
                .orElse(jwtRepository.save(RefreshToken.toEntity(jwtFactory.createRefreshToken(), userId, jwtProperty.getRefreshExpiration())));
        return new JwtTokenRes(jwtFactory.createAccessToken(userId), refreshToken.getValue());
    }

    public JwtTokenRes reIssueAccessToken(String token) {
        jwtProvider.verifyRefreshToken(token);
        RefreshToken refreshToken = jwtRepository.findById(token).orElseThrow(NotFoundRefreshTokenException::new);
        return new JwtTokenRes(jwtFactory.createAccessToken(refreshToken.getUserId()), token);
    }
}
