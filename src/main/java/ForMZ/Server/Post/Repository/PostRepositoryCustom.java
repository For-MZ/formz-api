package ForMZ.Server.Post.Repository;

import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.Post.Entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    List<Post> findUserPost(Long userId);
    List<Post> FindPost(String categoryName, List<String> words, final int startPage, final int pageSize);
    Optional<Post> FindPostById(Long id);
    List<Post> FindBestPost(Pageable pageable);
    List<Post> getDuplicationHouse();
    List<Post> findAllById(List<Long> id);
    List<Post> getAllHouse();
    List<BookMarkPost> BookMarkPostList(Long bookMarkId);
    List<Post> BookMark_Post(List<Long> bookMarkPostId);
}
