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
        jwtRepository.save(RefreshToken.toEntity(token, userId, 5000));

        // when
        Optional<RefreshToken> optional = jwtRepository.findById(token);

        // then
        assertThat(optional).isNotEmpty();
        RefreshToken refreshToken = optional.get();
        assertThat(refreshToken.getValue()).isEqualTo(token);
        assertThat(refreshToken.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Redis 삭제 - 정상")
    void deleteRedisTest() {
        // given
        String token = "리프레시 토큰";
        long userId = 1L;
        RefreshToken refreshToken = jwtRepository.save(RefreshToken.toEntity(token, userId, 5000));

        // when
        jwtRepository.delete(refreshToken);
        Optional<RefreshToken> optional = jwtRepository.findById(token);

        // then
        assertThat(optional).isEmpty();
    }

    @Test
    @DisplayName("Redis TTL 삭제 - 정상")
    void ttlTest() throws InterruptedException {
        // given
        String token = "리프레시 토큰";
        long userId = 1L;
        jwtRepository.save(RefreshToken.toEntity(token, userId, 1000));

        // when
        Thread.sleep(1000);
        Optional<RefreshToken> optional = jwtRepository.findById(token);

        // then
        assertThat(optional).isEmpty();
    }

    @Test
    @DisplayName("Secondary Index 탐색 시간 비교")
    void findSecondaryIndexTimeCheck() {
        // given
        int last = 10;
        for (int i = 0; i < last; i++) {
            jwtRepository.save(RefreshToken.toEntity("refresh" + i, i, 100000));
        }

        // when, then
        long start = System.nanoTime();
        Optional<RefreshToken> optional = jwtRepository.findByUserId(last);
        System.out.println("경과 시간 : " + ((System.nanoTime() - start) / 1_000_000) + "ms");
        // Secondary Index (X) -> 1만건 : 27ms, 5만건 : 155ms
        // Secondary Index (O) -> 1만건 : 24ms, 5만건 : 44ms
    }
}
