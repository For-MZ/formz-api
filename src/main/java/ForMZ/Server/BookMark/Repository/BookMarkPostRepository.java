package ForMZ.Server.BookMark.Repository;

import ForMZ.Server.BookMark.Entity.BookMarkPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkPostRepository extends JpaRepository<BookMarkPost,Long> {
}
