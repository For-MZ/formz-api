package ForMZ.Server.BookMark.Repository;

import ForMZ.Server.BookMark.Entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark,Long>,BookMarkRepositoryCustom {
}
