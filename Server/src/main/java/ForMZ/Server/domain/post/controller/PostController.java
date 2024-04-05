package ForMZ.Server.domain.post.controller;


import ForMZ.Server.domain.post.dto.*;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.service.PostService;
import ForMZ.Server.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ForMZ.Server.domain.post.constant.PostConstant.PostResponseMessage.*;

@RestController
@RequestMapping("/community")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     *  게시글 작성
     */
    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostReq postReq){
        postService.createPost(postReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_POST_SUCCESS.getMessage()));
    }

    /**
     *  게시글 수정
     */
    @PatchMapping("/posts/{postId}")
    public ResponseEntity updatePost(@RequestBody PostUpdateReq postUpdateReq,
                                     @PathVariable("postId") Long postId){
        Post post = postService.updatePost(postUpdateReq, postId);
        PostRes res = postService.convertPostRes(post);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), UPDATE_POST_SUCCESS.getMessage(), res));
    }

    /**
     *  게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.create(HttpStatus.NO_CONTENT.value(), DELETE_POST_SUCCESS.getMessage()));
    }

    /**
     *  게시글 목록 조회
     */
    @GetMapping("/posts")
    public ResponseEntity getPostList(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int size,
                                      @RequestParam(name = "category", required = false) String categoryCode,
                                      @RequestParam(name = "sort", defaultValue = "createdDate", required = false) String sort){
        AllPostRes postResList = postService.getPosts(sort, categoryCode, page-1, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_POSTS_SUCCESS.getMessage(), postResList));
    }

    /**
     *  특정 게시글 개별 조회
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") Long postId){
        Post post = postService.getPost(postId);
        postService.viewPlus(post);
        PostRes res = postService.convertPostRes(post);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_POST_SUCCESS.getMessage(), res));
    }

    /**
     * 검색을 통한 게시글 목록 조회
     */
    @GetMapping("/posts/search")
    public ResponseEntity getPostsBySearch(@RequestParam(name = "word") String word, @RequestParam(name = "page") int page) {
        AllPostsBySearch posts = postService.getPostsBySearch(word, page);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_POST_BY_SEARCH_SUCCESS.getMessage(), posts));
    }
}
