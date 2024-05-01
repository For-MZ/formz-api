package ForMZ.Server.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash("refreshToken")
public class RefreshToken {
    @Id
    private String value;

    @Indexed
    private long userId;

    @TimeToLive
    private long ttl;

    public static RefreshToken toEntity(String refreshToken, long userId, long milliSecond) {
        return RefreshToken.builder()
                .value(refreshToken)
                .userId(userId)
                .ttl(milliSecond / 1000)
                .build();
    }
}
