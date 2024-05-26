package ForMZ.Server.Post.Repository;

import ForMZ.Server.Post.Entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findUserPost(Long userId);
    List<Post> FindPost(String categoryName, List<String> words, Pageable pageable);
    Post FindPostById(Long id);
    List<Post> FindBestPost(Pageable pageable);
    List<Post> getDuplicationHouse();
}
