package ForMZ.Server.Comment.Service;

import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import ForMZ.Server.Comment.Dto.CommentDto;
import ForMZ.Server.Comment.Dto.ResCommentDto;
import ForMZ.Server.Comment.Entity.Comment;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Entity.PostType;
import ForMZ.Server.Post.Repository.PostRepository;
import ForMZ.Server.User.Dto.UserJoinDto;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import ForMZ.Server.User.Service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired CommentService commentService;
    @Autowired
    EntityManager em;

    @Test
    void userCommnetTest() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("id", "fjfkle352", "www@www.com", "user", "type", "/ee");
        userService.join(userJoinDto);
        User id = userRepository.findByUserId("id").get();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryRepository.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postRepository.saveAll(posts);
        List<Comment> comments = new ArrayList<>();
        for(int i=0;i<10;i++) {
            postRepository.findById(posts.get(0).getId());
            ResCommentDto commentDto = new ResCommentDto(id.getId(),posts.get(0).getId(),"a");
            commentService.addComment(commentDto);
        }
        em.flush();
        em.clear();
        List<CommentDto> userComment = commentService.findUserComment(id.getId());
        System.out.println(userComment);
    }


}