package ForMZ.Server.User.Repository;

import ForMZ.Server.User.Entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {


    Optional<User> UserWithBookMark(Long id);
}
