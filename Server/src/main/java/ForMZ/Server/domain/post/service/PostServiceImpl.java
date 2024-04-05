package ForMZ.Server.domain.post.service;

import ForMZ.Server.domain.bookmark.repository.BookmarkRepository;
import ForMZ.Server.domain.category.entity.Category;
import ForMZ.Server.domain.category.service.CategoryService;
import ForMZ.Server.domain.post.dto.*;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.exception.InvalidSortParamException;
import ForMZ.Server.domain.post.exception.PostBySerachNotFoundException;
import ForMZ.Server.domain.post.exception.PostNotFoundException;
import ForMZ.Server.domain.post.exception.UnauthorizedPostAccessException;
import ForMZ.Server.domain.post.mapper.PostMapper;
import ForMZ.Server.domain.post.repository.PostRepository;
import ForMZ.Server.domain.postLike.repository.PostLikeRepository;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ForMZ.Server.global.entity.BaseEntity.ObjectState.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final CategoryService categoryService;

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostLikeRepository postLikeRepository;

    private final PostMapper mapper;

    /**
     *  POST 생성
     */
    @Override
    @Transactional
    public void createPost(PostReq postReq){
        User user = userService.getCurrentUser();

        final String title = postReq.getTitle();
        final String text = postReq.getText();
        final String imageUrl = postReq.getImageUrl();
        final Category category = categoryService.getCategory(postReq.getCategory());

        Post post = new Post(user, category, title, text, imageUrl);
        post.changeState(ACT);

        postRepository.save(post);
    }

    /**
     *  1개의 POST 조회
     */
    @Override
    public Post getPost(Long postId) {
        Post post =  postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(post.getObjectState() == DEL)
            throw new PostNotFoundException();
        return post;
    }

    /**
     *  조회수 증가
     */
    @Override
    public void viewPlus(Post post){
        post.viewPlus();
    }

    /**
     *  POST 수정
     */
    @Override
    @Transactional
    public Post updatePost(PostUpdateReq postUpdateReq, Long postId){
        Post post = getPost(postId);
        verifyPostAuthority(post);

        final String title = postUpdateReq.getTitle();
        final String text = postUpdateReq.getText();
        final String imageUrl = postUpdateReq.getImageUrl();
        final Category category = categoryService.getCategory(postUpdateReq.getCategory());

        post.updatePost(category, title, text, imageUrl);

        return postRepository.save(post);
    }

    /**
     *  POST 삭제
     */
    @Override
    @Transactional
    public void deletePost(Long postId){
        Post post = getPost(postId);
        verifyPostAuthority(post);
        post.changeState(DEL);
    }

    /**
     *  모든 POST 조회
     */
    @Override
    public AllPostRes getPosts(String sort, String categoryName, int page, int size){
        List<Post> postList = new ArrayList();
        Category category = categoryService.getCategory(categoryName);

        switch (sort){
            case "createdDate" :
            case "views":
                postList = postRepository.findAll(PageRequest.of(page, size,
                       Sort.by(sort).descending())).getContent();
                break;
            case "likeCnt":
                postList = postRepository.findAll(PageRequest.of(page, size)).getContent().stream()
                        .sorted(Comparator.comparing(Post::getPostLikesCount))
                        .collect(Collectors.toList());
                break;
            case "commentCnt":
                postList = postRepository.findAll(PageRequest.of(page, size)).getContent().stream()
                        .sorted(Comparator.comparing(Post::getCommentsCount))
                        .collect(Collectors.toList());
                break;
        }

        if(!postList.isEmpty()){
            if(category != null){
                postList = postList.stream()
                        .filter(post -> post.getCategory().equals(category))
                        .collect(Collectors.toList());
            }
            return new AllPostRes(mapper.postListToAllPostRes(postList));
        }
            throw new InvalidSortParamException();
    }

    /**
     * 검색을 통한 게시글 목록 반환
     */
    @Override
    public AllPostsBySearch getPostsBySearch(String word, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Post> posts = postRepository.findPostsBySearchOrderByCreatedDate(word, pageable);
        // 일치하는 검색 결과가 없는 경우
        if (posts.isEmpty()) {
            throw new PostBySerachNotFoundException();
        }
        //TODO: Mapper를 통해 변경
        List<PostListRes> postsList = posts.stream()
                .map(p -> {
                    // 제목, 닉네임, 추천수, 조회 수, 댓글 수 반환
                    return PostListRes.builder()
                            .postId(p.getId())
                            .title(p.getTitle())
                            .category(p.getCategoryName())
                            .nickName(p.getUser().getNickName())
                            .likeCnt(p.getPostLikesCount())
                            .viewCnt(p.getViews())
                            .commentCnt(p.getCommentsCount())
                            .build();
                }).toList();
        AllPostsBySearch response = new AllPostsBySearch(postsList);
        return response;
    }

    /**
     *  POST -> POST_RES 변경해주는 메서드
     */
    public PostRes convertPostRes(Post post){
        User user = userService.getCurrentUser();

        final boolean bookmarked = checkUsersBookmarkedPost(post, user);
        final boolean liked = checkUsersPostLike(post, user);
        final int likeCnt = post.getPostLikesCount();
        final int commentCnt = post.getCommentsCount();
        final String categoryName = post.getCategory().getCategoryName().toString();

        return mapper.postToPostRes(post, bookmarked, liked, likeCnt, commentCnt, categoryName);
    }

    /**
     *  현재 유저가 북마크한 POST인지 확인
     */
    private boolean checkUsersBookmarkedPost(Post post, User user) {
        if(bookmarkRepository.findByUserAndPost(user, post).isPresent()){
            return true;
        }
        return false;
    }

    /**
     *  현재 유저가 좋아요 누른 POST인지 확인
     */
    private boolean checkUsersPostLike(Post post, User user) {
        if(postLikeRepository.findByUserAndPost(user, post).isPresent()){
            return true;
        }
        return false;
    }

    /**
     *  현재 사용자가 해당 POST에 대한 권한을 가지고 있는지 확인
     */
    private void verifyPostAuthority(Post post){
        User user = userService.getCurrentUser();
        if(!post.getUser().equals(user)){
            throw new UnauthorizedPostAccessException();
        }
    }
}
