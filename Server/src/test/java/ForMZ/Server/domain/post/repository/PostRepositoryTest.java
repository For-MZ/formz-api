package ForMZ.Server.domain.post.repository;

import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void saveUserAndPost() {
        //임시 유저 정보 저장
        User user = new User("formz@formz.com", "password", "formz", User.Role.USER, User.SignType.NORMAL);
        userRepository.save(user);
        // 임시 게시글 정보 저장
        IntStream.range(0, 5).forEach(
                i -> {
                    Post post = new Post(user, null, "title" + i, "content" + i, "");
                    postRepository.save(post);
                });
    }
    @AfterEach
    void clean() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("검색 결과 일치하는 게시글 목록 반환 - 제목 일치")
    void getPostsBySerch_Title() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findPostsBySearchOrderByCreatedDate("title", pageable);

        assertEquals(5, posts.size());
        assertEquals("title0", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("검색 결과 일치하는 게시글 목록 반환 - 내용 일치")
    void getPostsBySerch_Text() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findPostsBySearchOrderByCreatedDate("content", pageable);

        assertEquals(5, posts.size());
        assertEquals("content0", posts.get(0).getText());
    }

    @Test
    @DisplayName("검색 결과 일치하는 게시글 목록 반환 - 닉네임 일치")
    void getPostsBySerch_NickName() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findPostsBySearchOrderByCreatedDate("formz", pageable);

        assertEquals(5, posts.size());
        assertEquals("formz", posts.get(0).getUser().getNickName());
    }
}
