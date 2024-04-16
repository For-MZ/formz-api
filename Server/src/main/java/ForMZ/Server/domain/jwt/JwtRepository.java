package ForMZ.Server.domain.jwt;

import org.springframework.data.repository.CrudRepository;

public interface JwtRepository extends CrudRepository<RefreshToken, String> {
}