package ForMZ.Server.domain.bookmark.repository;

import ForMZ.Server.domain.bookmark.entity.Bookmark;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserAndPost(User user, Post post);
}
