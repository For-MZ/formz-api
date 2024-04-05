package ForMZ.Server.domain.post.service;

import ForMZ.Server.domain.post.dto.*;
import ForMZ.Server.domain.post.entity.Post;

public interface PostService {
    void createPost(PostReq postReq);
    Post getPost(Long postId);
    Post updatePost(PostUpdateReq postUpdateReq, Long postId);
    void deletePost(Long postId);
    AllPostRes getPosts(String sort, String categoryCode, int page, int size);
    PostRes convertPostRes(Post post);
    void viewPlus(Post post);
    AllPostsBySearch getPostsBySearch(String word, int page);
}
