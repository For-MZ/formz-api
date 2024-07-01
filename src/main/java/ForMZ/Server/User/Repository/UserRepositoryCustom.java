package ForMZ.Server.User.Repository;

import ForMZ.Server.User.Entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUserEmail(String id);
    Optional<User> UserWithBookMark(Long id);
}
