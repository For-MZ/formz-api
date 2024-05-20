package ForMZ.Server.User.Repository;

import ForMZ.Server.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {

}
