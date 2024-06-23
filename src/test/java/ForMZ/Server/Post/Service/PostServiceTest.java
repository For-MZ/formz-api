package ForMZ.Server.Post.Service;

import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import ForMZ.Server.Post.Dto.PostDto;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Entity.PostType;
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
import java.util.Optional;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EntityManager em;
    @Autowired PostService postService;
    @Test
    public void userPost(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        User id = userRepository.findByUserId("id").get();
        List<Post> posts= new ArrayList<>();
        Category category = new Category("임시");
        categoryRepository.save(category);
        for(int i=0;i<10;i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        List<PostDto> userPost = postService.findUserPost(id.getId());
        for(int i =0;i<10;i++){
            System.out.println(userPost.get(i));
        }
    }
}