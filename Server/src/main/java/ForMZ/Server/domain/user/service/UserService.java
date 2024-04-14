package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.user.dto.LoginRes;
import ForMZ.Server.domain.user.dto.UserReq;
import ForMZ.Server.domain.user.entity.User;

public interface UserService {
    User getUser(Long userId);
    User getCurrentUser();
    void createUser(UserReq userReq);
    User getUserByEmail(String email);

    LoginRes loginOAuth(String target, String code);
}
