package ForMZ.Server.Post.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.BookMark.Repository.BookMarkPostRepository;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.BookMark.Service.BookMarkPostService;
import ForMZ.Server.BookMark.Service.BookMarkService;
import ForMZ.Server.Category.Service.CategoryService;
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
    EntityManager em;
    @Autowired PostService postService;
    @Autowired
    BookMarkService bookMarkService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookMarkPostService bookMarkPostService;

    @Test
    public void userPost(){
        UserJoinDto userJoinDto = new UserJoinDto("id","fjfkle352","www@www.com","user","type","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("id").get();
        BookMark bookMark = new BookMark();
        List<Post> posts= new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
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
        bookMarkService.save(bookMark);
        User id = userService.findByUserId("id").get();
        id.settingBookMark(bookMark);
        em.flush();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        List<BookMarkPost> bookMarkPostList = new ArrayList<>();
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            BookMarkPost bookMarkPost = new BookMarkPost(post, id.getBookMark());
            bookMarkPostList.add(bookMarkPost);
            posts.add(post);
        }
        postService.saveAll(posts);
        bookMarkPostService.saveAll(bookMarkPostList);

        em.flush();
        em.clear();
        BookMark bookMark1 = id.getBookMark();

        List<Long> d = postService.BookMarkPostList(bookMark1.getId()).stream().map(BookMarkPost::getId).toList();
        List<PostDto> posts1 = postService.bookMark_Post(d);
        for (PostDto post : posts1) {
            System.out.println(post.toString());
        }
    }
}