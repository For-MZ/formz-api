package ForMZ.Server.domain.jwt;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtFactory jwtFactory;
    private final JwtProperty jwtProperty;
    private final JwtRepository jwtRepository;

    @Transactional
    public JwtToken createJwtToken(long userId) {
        RefreshToken refreshToken = jwtRepository.findByUserId(userId)
                .orElse(jwtRepository.save(RefreshToken.toEntity(jwtFactory.createRefreshToken(), userId, jwtProperty.getRefreshExpiration())));
        return new JwtToken(jwtFactory.createAccessToken(userId), refreshToken.getValue());
    }
}
