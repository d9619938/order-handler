package com.local.orderhandler.repository;

import com.local.orderhandler.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<User, Integer> {
    Optional<User> getUserByUsername(String username);
    boolean existsByUsername (String username);
}
