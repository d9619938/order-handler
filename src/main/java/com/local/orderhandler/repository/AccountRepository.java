package com.local.orderhandler.repository;

import com.local.orderhandler.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<User, Integer> {
}
