package ForMZ.Server.domain.jwt;

import ForMZ.Server.global.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(RedisConfig.class)
@DataRedisTest
@Testcontainers
public class JwtRepositoryTest {

    @Autowired
    JwtRepository jwtRepository;

    @Container
    private static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"))
                    .withExposedPorts(6379);

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("redis.host", redis::getHost);
        registry.add("redis.port", () -> redis.getMappedPort(6379));
    }

    @Test
    @DisplayName("Redis 저장 및 조회 - 정상")
    void saveRedisTest() {
        // given
        String token = "리프레시 토큰";
        long userId = 1L;
        jwtRepository.save(RefreshToken.toEntity(token, userId));

        // when
        Optional<RefreshToken> optional = jwtRepository.findById(token);

        // then
        assertThat(optional).isNotEmpty();
        RefreshToken refreshToken = optional.get();
        assertThat(refreshToken.getRefreshToken()).isEqualTo(token);
        assertThat(refreshToken.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Redis 삭제 - 정상")
    void deleteRedisTest() {
        // given
        String token = "리프레시 토큰";
        long userId = 1L;
        RefreshToken refreshToken = jwtRepository.save(RefreshToken.toEntity(token, userId));

        // when
        jwtRepository.delete(refreshToken);
        Optional<RefreshToken> optional = jwtRepository.findById(token);

        // then
        assertThat(optional).isEmpty();
    }
}
