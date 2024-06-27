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
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Dto.UserDto;
import ForMZ.Server.User.Repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkPostRepository bookMarPostRepository;

    public List<PostDto> findUserPost(Long userId){
        List<Post> posts = postRepository.findUserPost(userId);
        return posts.stream().map(this::getPostDto).toList();
    }
    //11번
    public List<PostDto> findPosts(@Nullable String category_name, List<String> words,final int startPage, final int PageSize){
        List<Post> posts = postRepository.FindPost(category_name, words, startPage,PageSize);
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

    public void SavePost(ResPostDto postDto) throws Exception {
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        if(category.isPresent()){
            Post post = new Post(postDto.getTitle(),postDto.getContent(), postDto.getView_count(), postDto.getLike_count(), PostType.posts);
            post.setCategories(category.get());
            postRepository.save(post);
        }
        else{
            throw new Exception("존재하지 않은 카테고리입니다");
        }
    }

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
    public void deletePost(Long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지 않는 게시물입니다");
        postRepository.delete(post.get());
    }

    public void BookMarkPost(Long postId,Long userId) throws Exception {
        Optional<User> user = userRepository.UserWithBookMark(userId);
        if (user.isEmpty()) throw new Exception("존재하지않는 사용자입니다");
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지않는 게시물입니다");
        BookMarkPost bookMarkPost = new BookMarkPost(post.get(),user.get().getBookMark());
        bookMarPostRepository.save(bookMarkPost);
        //추후 북마크내의 타입을 지정해서 청년공간,청년정책,청년주택,게시글 로 나눔;
        //또한 게시물에도 타입을 지정
    }
    //26

    public List<Long> UserBookMarkPostId(Long UserId){
        BookMark bookMark = bookMarkRepository.UserBookMark(UserId);
        List<Long> bookMarkPostId = bookMarkRepository.findBookMarkPostId(bookMark.getId());
        List<Post> posts = postRepository.findAllById(bookMarkPostId);
        return posts.stream().map(Post::getId).toList();
    }
    //13

    public List<Long> UserBookMarkHouseId(Long UserId){
        BookMark bookMark = bookMarkRepository.UserBookMark(UserId);
        List<Long> bookMarkPostId = bookMarkRepository.findBookMarkHouseId(bookMark.getId());
        List<Post> posts = postRepository.findAllById(bookMarkPostId);
        return posts.stream().map(Post::getId).toList();
    }
    public void saveAll(List<Post> posts){
        postRepository.saveAll(posts);
    }
    public List<BookMarkPost> BookMarkPostList(Long bookMarkId){
        return postRepository.BookMarkPostList(bookMarkId);
    }
    public List<PostDto> bookMark_Post(List<Long> bookMarkId){
        return postRepository.BookMark_Post(bookMarkId).stream().map(this::getPostDto).toList();
    }

    private PostDto getPostDto(Post post) {
        User postUser = post.getUser();
        UserDto userDto = new UserDto(postUser.getLoginId(), postUser.getEmail(), postUser.getNickname(), postUser.getProfileImageUrl());
        return new PostDto(post.getId(), post.getTitle(), userDto, post.getCategories().getCategoryName(), post.getCreatedDate(), post.getLastModifiedDate(),
                post.getLike_count(), post.getView_count(), post.getCommentList().size());
    }

    private PostDetailDto getPostDetailDto(Post post) {
        User postUser = post.getUser();
        UserDto userDto = new UserDto(postUser.getLoginId(), postUser.getEmail(), postUser.getNickname(), postUser.getProfileImageUrl());
        return new PostDetailDto(post.getId(), post.getTitle(),post.getContent(), userDto, post.getCategories().getCategoryName(), post.getCreatedDate(), post.getLastModifiedDate(),
                post.getLike_count(), post.getView_count(), post.getCommentList().size());
    }

}
