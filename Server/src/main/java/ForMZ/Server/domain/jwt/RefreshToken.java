package ForMZ.Server.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class RefreshToken {
    @Id
    private String refreshToken;

    private long userId;

    public static RefreshToken toEntity(String refreshToken, long userId) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }
}
