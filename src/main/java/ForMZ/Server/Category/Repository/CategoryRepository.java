package ForMZ.Server.Category.Repository;

import ForMZ.Server.Category.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    Optional<Category> findByCategoryName(String name);
}
