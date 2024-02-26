
package com.local.orderhandler.repository;

import com.local.orderhandler.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Integer> {
//    @Query(nativeQuery = true, value = "SELECT * FROM tb_users WHERE username = :username")
    Optional<User> findByUsername(String username); //нужно для аутентификации пользователя
}
