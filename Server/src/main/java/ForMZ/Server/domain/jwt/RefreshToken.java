package ForMZ.Server.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash("refreshToken")
public class RefreshToken {
    @Id
    private String value;

    private long userId;

    @TimeToLive
    private long ttl;

    public static RefreshToken toEntity(String refreshToken, long userId) {
        return RefreshToken.builder()
                .value(refreshToken)
                .userId(userId)
                .ttl(Long.parseLong(System.getProperty("jwt.refresh.expiration")) / 1000)
                .build();
    }
}
