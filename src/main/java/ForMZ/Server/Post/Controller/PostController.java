package ForMZ.Server.Post.Controller;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.Configuration.JwtTokenUtil;
import ForMZ.Server.Core.Dto.JoinDto;
import ForMZ.Server.Post.Dto.*;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Service.PostService;
import ForMZ.Server.User.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ForMZ.Server.Post.Entity.QPost.post;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/api/user/post")
    public ResponseEntity<List<PostDto>> userPost(HttpServletRequest request) throws Exception {
        try {
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            Long userId = userService.findUserProfile(accessToken).getId();
            List<PostDto> userPost = postService.findUserPost(userId);
            return ResponseEntity.status(200).body(userPost);
        }
        catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }
    @GetMapping("/api/user/bookmark")
    public ResponseEntity<List<PostDto>> userBookMarkPost(HttpServletRequest request){
        try {
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            String email = jwtTokenUtil.getclaims(accessToken).getSubject();
            BookMark bookMark = userService.findByUserId(email).get().getBookMark();
            List<Long> d = postService.BookMarkPostList(bookMark.getId()).stream().map(BookMarkPost::getId).toList();
            List<PostDto> posts = postService.bookMark_Post(d);
            return ResponseEntity.status(200).body(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }
    @PostMapping("/api/post")
    public ResponseEntity<JoinDto> userAddPost(HttpServletRequest request,@RequestBody ResPostDto resPostDto){
        try {
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            String email = jwtTokenUtil.getclaims(accessToken).getSubject();
            Long post_id = postService.savePost(resPostDto,email);
            JoinDto joinDto = new JoinDto(post_id, "게시글이 성공적으로 작성되었습니다.");
            return ResponseEntity.status(201).body(joinDto);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
    @PostMapping("/api/posts")
    public ResponseEntity<List<PostDto>> AllPost(@RequestBody ResPostsDto resPostsDto,HttpServletRequest request){
        String accessToken = jwtTokenUtil.resolveAccessToken(request);
        String email = jwtTokenUtil.getclaims(accessToken).getSubject();
        int pageNumber = resPostsDto.getPage();
        List<PostDto> posts = postService.findPosts(resPostsDto.getCategory(), resPostsDto.getWord(), pageNumber, 10,email);
        return ResponseEntity.status(200).body(posts);
    }
    @PostMapping("/api/post/{postId}")
    public ResponseEntity<PostDetailDto> DetailPost(@PathVariable("postId") Long postId){
        try {
            PostDetailDto post = postService.findPost(postId);
            return ResponseEntity.status(200).body(post);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/api/post/{postId}")
    public ResponseEntity<String> changePost(@RequestBody ResChangePostDto changePostDto, @PathVariable("postId") Long postId){
        try {
            postService.ChangePost(changePostDto,postId);
            return ResponseEntity.status(200).body("게시글이 성공적으로 수정되었습니다");
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<String> deletePost( @PathVariable("postId") Long postId){
        try {
            postService.deletePost(postId);
            return ResponseEntity.status(204).body("게시글이 성공적으로 삭제되었습니다");
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/community/posts/popular")
    public ResponseEntity<List<PostDto>> bestPosts(){
        try {
            List<PostDto> postDtos = postService.BestPost(10);
            return ResponseEntity.status(200).body(postDtos);
        }
        catch (Exception e){
            return ResponseEntity.status(403).build();
        }
    }
    @PatchMapping("/community/posts/{postId}/like")
    public ResponseEntity<BookMarkPostDto> BookmarkPost(@PathVariable("postId") Long postId,HttpServletRequest request){
        try{
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            String email = jwtTokenUtil.getclaims(accessToken).getSubject();
            postService.BookMarkPost(postId,email);
            BookMarkPostDto dtos = new BookMarkPostDto("true");
            return ResponseEntity.status(200).body(dtos);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }
}
