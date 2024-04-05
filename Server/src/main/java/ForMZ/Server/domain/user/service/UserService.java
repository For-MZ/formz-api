package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.global.auth.jwt.dto.JwtToken;
import ForMZ.Server.global.auth.jwt.dto.LoginReq;

public interface UserService {
    User getUser(Long userId);
    User getCurrentUser();
    JwtToken Login(LoginReq loginReq);
}
