package ForMZ.Server.Post.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import ForMZ.Server.Post.Dto.PostDto;
import ForMZ.Server.Post.Dto.ResChangePostDto;
import ForMZ.Server.Post.Dto.ResPostDto;
import ForMZ.Server.Post.Entity.Post;
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

    public List<PostDto> findUserPost(Long userId){
        List<Post> posts = postRepository.findUserPost(userId);
        return posts.stream().map(this::getPostDto).toList();
    }
    //11번
    public List<PostDto> findPosts(@Nullable String category_name, List<String> words, Pageable pageable){
        List<Post> posts = postRepository.FindPost(category_name, words, pageable);
        return posts.stream().map(this::getPostDto).toList();
    }
    //20번
    public PostDto findPost(Long postId){
        Post post = postRepository.FindPostById(postId);
        return getPostDto(post);
    }

    public List<PostDto> BestPost(Pageable pageable){
        List<Post> posts = postRepository.FindBestPost(pageable);
        return posts.stream().map(this::getPostDto).toList();
    }

    public void SavePost(ResPostDto postDto) throws Exception {
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        if(category.isPresent()){
            Post post = new Post(postDto.getTitle(),postDto.getContent(), postDto.getView_count(), postDto.getLike_count());
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

    private void BookMarkPost(Long postId,Long userId) throws Exception {
        Optional<User> user = userRepository.UserWithBookMark(userId);
        if (user.isEmpty()) throw new Exception("존재하지않는 사용자입니다");
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new Exception("존재하지않는 게시물입니다");
        BookMarkPost bookMarkPost = new BookMarkPost(post.get(),user.get().getBookMarks().get(0));
        //추후 북마크내의 타입을 지정해서 청년공간,청년정책,청년주택,게시글 로 나눔;
        //또한 게시물에도 타입을 지정
    }
    //26


    private PostDto getPostDto(Post post) {
        User postUser = post.getUser();
        UserDto userDto = new UserDto(postUser.getId(), postUser.getEmail(), postUser.getNickname(), postUser.getProfileImageUrl());
        return new PostDto(post.getId(), post.getTitle(), userDto, post.getCategories().getCategoryName(), post.getCreatedDate(), post.getLastModifiedDate(),
                post.getLike_count(), post.getView_count(), post.getCommentList().size());
    }

}
