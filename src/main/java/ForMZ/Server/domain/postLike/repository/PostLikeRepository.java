package ForMZ.Server.domain.postLike.repository;

import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.postLike.entity.PostLike;
import ForMZ.Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
