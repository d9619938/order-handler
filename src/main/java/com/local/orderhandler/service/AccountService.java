package com.local.orderhandler.service;

import com.local.orderhandler.entity.Role;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.AccountRepository;
import com.local.orderhandler.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void saveUser(User user) throws HandlerException {
        if(accountRepository.existsById(user.getId())) {
            throw new HandlerException("Пользователь с id " + user.getId() + " уже существует");
        }
        user.setRole(roleRepository.findByRoleType(Role.RoleType.ROLE_ADMIN).orElseThrow(()-> new HandlerException("ошибка в роли")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        accountRepository.save(user);
    }

    public void update(User user) throws HandlerException{
        if(!accountRepository.existsById(user.getId())){
            throw new HandlerException("Пользователь с id " + user.getId() + " не найден");
        }
        accountRepository.save(user);
    }

    public List<User> getAllUsers() throws HandlerException{
        Iterable<User> iterableUsers = accountRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);
        if(userList.isEmpty()){
            throw new HandlerException("Список пользователей пуст");
        }
        return userList;
    }

    public void deleteUser(int id) throws HandlerException {
        if(!accountRepository.existsById(id)){
            throw new HandlerException("Пользователь с id " + id + " не найден");
        }
        accountRepository.deleteById(id);
    }

    public User getUserById(int id) throws HandlerException {
        return accountRepository.findById(id).orElseThrow(() -> new HeadlessException("Пользователь с id " + id + " не найден"));
    }
}
