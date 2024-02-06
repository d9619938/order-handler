package com.local.orderhandler.repository;

import com.local.orderhandler.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_users WHERE namt =:name")
    User getUserByName(@NotNull(message = "name not null")
                       @NotEmpty(message = "name not empty")
                       @Param("name")
                       String name);

}
