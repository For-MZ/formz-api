package ForMZ.Server.domain.post.repository;

import ForMZ.Server.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.title like %:word% or p.text like %:word% or p.user.nickName like %:word% order by p.createdDate desc")
    List<Post> findPostsBySearchOrderByCreatedDate(@Param(value = "word") String word, Pageable pageable);
}
