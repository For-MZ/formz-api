package ForMZ.Server.Post.Repository;

import ForMZ.Server.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> , PostRepositoryCustom {

}
