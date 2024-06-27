package ForMZ.Server.Post.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.BookMark.Repository.BookMarkPostRepository;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import ForMZ.Server.Post.Dto.PostDto;
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
    @Autowired
    BookMarkRepository bookMarkRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    BookMarkPostRepository bookMarkPostRepository;
    @Test
    public void userPost(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        User id = userRepository.findByUserId("id").get();
        BookMark bookMark = new BookMark();
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
    @Test
    public void userBookMarkPost() {
        UserJoinDto userJoinDto = new UserJoinDto("id", "fjfkle352", "www@www.com", "user", "type", "/ee");
        userService.join(userJoinDto);
        BookMark bookMark = new BookMark();
        bookMarkRepository.save(bookMark);
        User id = userRepository.findByUserId("id").get();
        id.settingBookMark(bookMark);
        em.flush();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        List<BookMarkPost> bookMarkPostList = new ArrayList<>();
        categoryRepository.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            BookMarkPost bookMarkPost = new BookMarkPost(post, id.getBookMark());
            bookMarkPostList.add(bookMarkPost);
            posts.add(post);
        }
        postService.saveAll(posts);
        bookMarkPostRepository.saveAll(bookMarkPostList);

        em.flush();
        em.clear();
        BookMark bookMark1 = id.getBookMark();

        List<Long> d = postRepository.BookMarkPostList(bookMark1.getId()).stream().map(b->{
            return b.getId();
        }).toList();
        List<Post> posts1 = postRepository.BookMark_Post(d);
        for (Post post : posts1) {
            System.out.println(post.toString());
        }
    }
}