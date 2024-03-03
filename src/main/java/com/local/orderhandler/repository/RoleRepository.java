package com.local.orderhandler.repository;

import com.local.orderhandler.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    boolean existsByRoleType(Role.RoleType roleType);
    Optional<Role> findByRoleType (Role.RoleType roleType);
}
