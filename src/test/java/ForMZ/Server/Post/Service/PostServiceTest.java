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
import ForMZ.Server.Post.Dto.PostDetailDto;
import ForMZ.Server.Post.Dto.PostDto;
import ForMZ.Server.Post.Dto.ResChangePostDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.*;

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
    public void userPost() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
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
    public void userBookMarkPost() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        BookMark bookMark = new BookMark();
        bookMarkService.save(bookMark);
        User id = userService.findByUserId("www@www.com").get();
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
    @Test
    public void find_posts() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
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
        List<String> find_word = new ArrayList<>();
//        //List<PostDto> posts1 = postService.findPosts("비션", find_word, 0, 10);
//        for (PostDto postDto : posts1) {
//            System.out.println(postDto);
//        }
    }
    @Test
    public void find_post_error() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        try{
            PostDetailDto post = postService.findPost(11L);
            System.out.println(post);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void find_post_normal() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        try{
            PostDetailDto post = postService.findPost(8L);
            System.out.println(post);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void change_post_error() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        //ResChangePostDto changePostDto = new ResChangePostDto("k","kk","임시2");//카테고리 에러
        ResChangePostDto changePostDto = new ResChangePostDto("k","kk","임시");
        //Post post = posts.get(0);
        try {
            postService.ChangePost(changePostDto, 11L);//게시물 미존재 에러
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void change_post_normal() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        ResChangePostDto changePostDto = new ResChangePostDto("k","kk","임시");
        Post post = posts.get(0);
        try {
            postService.ChangePost(changePostDto, post.getId());//게시물 미존재 에러
            System.out.println("성공");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void delete_post_error() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        try {
            postService.deletePost(11L);
            System.out.println("성공");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void delete_post_normal() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 10; i++) {
            Post post = new Post("1", "test1", 0, 0, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        try {
            postService.deletePost(7L);
            System.out.println("성공");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void best_post_normal() throws Exception {
        UserJoinDto userJoinDto = new UserJoinDto("fjfkle352","www@www.com","user","/ee");
        userService.join(userJoinDto);
        User id = userService.findByUserId("www@www.com").get();
        BookMark bookMark = new BookMark();
        List<Post> posts = new ArrayList<>();
        Category category = new Category("임시");
        categoryService.save(category);
        for (int i = 0; i < 20; i++) {
            Post post = new Post("1", "test1", 100-i, 100-i, PostType.posts);
            post.setUser(id);
            post.setCategories(category);
            posts.add(post);
        }
        postService.saveAll(posts);
        em.flush();
        em.clear();
        try {
            List<PostDto> postDtos = postService.BestPost(10);
            for (PostDto postDto : postDtos) {
                System.out.println(postDto);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void book_mark_post(){

    }
}