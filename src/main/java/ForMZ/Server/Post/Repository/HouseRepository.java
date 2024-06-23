package ForMZ.Server.Post.Repository;

import ForMZ.Server.Post.Entity.House;
import ForMZ.Server.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House,Long> {

}
