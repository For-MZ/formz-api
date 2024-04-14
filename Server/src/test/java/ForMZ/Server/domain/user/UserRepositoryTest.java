package ForMZ.Server.domain.user;

import ForMZ.Server.domain.user.entity.SignType;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("Find By SignType, SocialId 정상")
    void checkFindBySignTypeAndSocialId() {
        // given
        SignType signType = SignType.GOOGLE;
        String socialId = "test";
        userRepository.save(makeUser(signType, socialId));
        
        // when
        Optional<User> optional = userRepository.findBySignTypeAndSocialId(signType, socialId);

        // then
        assertThat(optional).isNotEmpty();
    }

    @Disabled
    @Test
    @DisplayName("SignType, SocialId Index 조회 성능 테스트")
    void performanceTestByIndex() {
        // given
        SignType signType = SignType.GOOGLE;
        String socialId = "test";
        saveUserEntityByCount(100000);
        userRepository.save(makeUser(signType, socialId));
        em.clear();

        // when
        Date now = new Date();
        userRepository.findBySignTypeAndSocialId(SignType.GOOGLE, "test");
        System.out.println("경과 시간 : " + (new Date().getTime() - now.getTime()));

        em.clear();
    }

    private void saveUserEntityByCount(int count) {
        List<User> userList = new ArrayList<>();

        while (count -- > 0) {
            userList.add(makeUser(SignType.values()[count % SignType.values().length], UUID.randomUUID().toString()));
        }

        userRepository.saveAll(userList);
    }

    private User makeUser(SignType signType, String socialId) {
        return User.builder()
                .email("test@test.com")
                .nickName("test")
                .signType(signType)
                .socialId(socialId)
                .build();
    }
}
