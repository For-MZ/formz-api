package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.jwt.JwtToken;
import ForMZ.Server.domain.user.dto.UserReq;
import ForMZ.Server.domain.user.entity.User;

public interface UserService {
    User getUser(Long userId);
    User getCurrentUser();
    void createUser(UserReq userReq);
    User getUserByEmail(String email);

    JwtToken loginOAuth(String target, String code);
}
