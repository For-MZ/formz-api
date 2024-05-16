package com.example.demo.Repository.User;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryCustom {

}
