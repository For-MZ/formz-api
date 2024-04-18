package ForMZ.Server.domain.jwt;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserId(long userId);
}