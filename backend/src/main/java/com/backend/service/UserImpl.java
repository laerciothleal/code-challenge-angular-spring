package com.backend.service;

import com.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserImpl {
    List<User> findAll();

    List<User> findByNameContaining(String name);

    Optional<User> findById(long id);

    User save(User user);

    User update(long id, User user);

    void deleteById(long id);

    void deleteAll();
}
