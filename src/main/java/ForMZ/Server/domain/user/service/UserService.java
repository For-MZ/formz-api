package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.jwt.JwtTokenRes;
import ForMZ.Server.domain.user.dto.UserReq;
import ForMZ.Server.domain.user.entity.User;

public interface UserService {
    User getUser(Long userId);
    User getCurrentUser();
    void createUser(UserReq userReq);
    User getUserByEmail(String email);

    JwtTokenRes loginOAuth(String target, String code);
}
