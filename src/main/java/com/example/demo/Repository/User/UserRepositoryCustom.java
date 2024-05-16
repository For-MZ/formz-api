package com.example.demo.Repository.User;

import com.example.demo.Entity.User;

public interface UserRepositoryCustom {
    User UserWithSearchHistory(Long id);

    User UserWithBookMark(Long id);
    User UserWithPost(Long id);
    User UserWithComment(Long id);
}
