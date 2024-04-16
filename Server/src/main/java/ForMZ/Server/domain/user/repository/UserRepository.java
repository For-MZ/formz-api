package ForMZ.Server.domain.user.repository;

import ForMZ.Server.domain.user.entity.SignType;
import ForMZ.Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findBySignTypeAndSocialId(SignType social, String socialId);
}
