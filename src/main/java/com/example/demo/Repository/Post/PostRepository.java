package com.example.demo.Repository.Post;

import com.example.demo.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> , PostRepositoryCustom {

}
