package ForMZ.Server.User.Service;

import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
