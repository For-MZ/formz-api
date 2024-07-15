package ForMZ.Server.Post.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.BookMark.Repository.BookMarkPostRepository;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import ForMZ.Server.Post.Dto.PostDetailDto;
import ForMZ.Server.Post.Dto.PostDto;
import ForMZ.Server.Post.Dto.ResChangePostDto;
import ForMZ.Server.Post.Dto.ResPostDto;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Entity.PostType;
import ForMZ.Server.Post.Repository.PostRepository;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.SearchHistory.Repository.SearchHistoryRepository;
import ForMZ.Server.SearchHistory.Service.SearchHistoryService;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Dto.UserDto;
import ForMZ.Server.User.Repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkPostRepository bookMarPostRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public List<PostDto> findUserPost(Long userId){
        List<Post> posts = postRepository.findUserPost(userId);
        return posts.stream().map(this::getPostDto).toList();
    }
    //11번
    @Transactional
    public List<PostDto> findPosts(@Nullable String category_name, @Nullable String word,final int startPage, final int PageSize,String userEmail){
        List<Post> posts = postRepository.FindPost(category_name, word, startPage,PageSize);
        if(word!=null){
            User user = userRepository.findByUserEmail(userEmail).get();
            searchHistoryRepository.save(new SearchHistory(word,user));
        }
        return posts.stream().map(this::getPostDto).toList();
    }
    //20번
    public PostDetailDto findPost(Long postId) throws Exception {
        Optional<Post> post = postRepository.FindPostById(postId);
        if(post.isEmpty()){
            throw new Exception("존재하지 않는 게시물입니다");
        }
        return getPostDetailDto(post.orElse(null));
    }

    public List<PostDto> BestPost(int PageSize){
        List<Post> posts = postRepository.FindBestPost(PageSize);
        return posts.stream().map(this::getPostDto).toList();
    }

    @Transactional
    public Long savePost(ResPostDto postDto,String email) throws Exception {
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        Optional<User> byUserEmail = userRepository.findByUserEmail(email);
        if (byUserEmail.isEmpty()){
            throw new Exception("잘못된 접근입니다.");
        }
        if(category.isPresent()){
            Post post = new Post(postDto.getTitle(),postDto.getContent(), postDto.getView_count(), postDto.getLike_count(), PostType.posts);
            post.setCategories(category.get());
            post.setUser(byUserEmail.get());
            postRepository.save(post);
            return post.getId();
        }
        else{
            throw new Exception("존재하지 않은 카테고리입니다");
        }
    }
    @Transactional
    public void ChangePost(ResChangePostDto changePostDto,Long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지 않는 게시물입니다");
        Optional<Category> category = categoryRepository.findByCategoryName(changePostDto.getCategoryName());
        if(category.isEmpty()) throw new Exception("존재하지 않는 카테고리입니다");
        Post find_post = post.get();
        find_post.changePost(changePostDto.getTitle(),changePostDto.getContent());
        find_post.setCategories(category.get());
        postRepository.save(find_post);
    }
    @Transactional
    public void deletePost(Long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지 않는 게시물입니다");
        postRepository.delete(post.get());
    }

    @Transactional
    public void saveAll(List<Post> posts){
        postRepository.saveAll(posts);
    }
    public List<BookMarkPost> BookMarkPostList(Long bookMarkId){
        return postRepository.BookMarkPostList(bookMarkId);
    }
    public List<PostDto> bookMark_Post(List<Long> bookMarkId){
        return postRepository.BookMark_Post(bookMarkId).stream().map(this::getPostDto).toList();
    }
    @Transactional
    public void BookMarkPost(Long postId,String userEmail) throws Exception {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        if(user.isEmpty()) {
            throw new Exception("존재하지않는 회원입니다.");
        }
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지 않는 게시물입니다");
        BookMarkPost bookMarkPost = new BookMarkPost(post.get(), user.get().getBookMark());
        bookMarPostRepository.save(bookMarkPost);
    }
    private PostDto getPostDto(Post post) {
        User postUser = post.getUser();
        UserDto userDto = new UserDto(postUser.getId(), postUser.getEmail(), postUser.getNickname(), postUser.getProfileImageUrl());
        return new PostDto(post.getId(), post.getTitle(), userDto, post.getCategories().getCategoryName(), post.getCreatedDate(), post.getLastModifiedDate(),
                post.getLike_count(), post.getView_count(), post.getCommentList().size());
    }

    private PostDetailDto getPostDetailDto(Post post) {
        User postUser = post.getUser();
        UserDto userDto = new UserDto(postUser.getId(), postUser.getEmail(), postUser.getNickname(), postUser.getProfileImageUrl());
        return new PostDetailDto(post.getId(), post.getTitle(),post.getContent(), userDto, post.getCategories().getCategoryName(), post.getCreatedDate(), post.getLastModifiedDate(),
                post.getLike_count(), post.getView_count(), post.getCommentList().size());
    }

}
