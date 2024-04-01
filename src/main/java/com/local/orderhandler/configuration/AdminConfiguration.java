package com.local.orderhandler.configuration;

import com.local.orderhandler.entity.Role;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.RoleRepository;
import com.local.orderhandler.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class AdminConfiguration {
    private final AccountService accountService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminConfiguration(AccountService accountService, RoleRepository roleRepository) {
        this.accountService = accountService;
        this.roleRepository = roleRepository;
    }


    @Bean
    public User adminSetUp () throws HandlerException {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("mebel-klick@yandex.ru");
        accountService.saveAdminDefault(admin);
        return admin;
    }

    @Bean
    public Role role () {
        Role roleAdmin = new Role(1, Role.RoleType.ROLE_ADMIN);
        roleRepository.save(roleAdmin);
        return roleAdmin;
    }

}
