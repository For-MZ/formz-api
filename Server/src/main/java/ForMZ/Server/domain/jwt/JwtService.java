package ForMZ.Server.domain.jwt;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtFactory jwtFactory;
    private final JwtRepository jwtRepository;

    @Transactional
    public JwtToken createJwtToken(long userId) {
        RefreshToken refreshToken = jwtRepository.save(RefreshToken.toEntity(jwtFactory.createRefreshToken(), userId));
        return new JwtToken(jwtFactory.createAccessToken(userId), refreshToken.getValue());
    }
}
