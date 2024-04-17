package ForMZ.Server.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
        String ttl = new AnnotationConfigApplicationContext().getEnvironment().getProperty("jwt.refresh.expiration");
        return RefreshToken.builder()
                .value(refreshToken)
                .userId(userId)
                .ttl(Long.parseLong(ttl) / 1000)
                .build();
    }
}
