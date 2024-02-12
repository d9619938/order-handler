package com.local.orderhandler.service;

import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void saveUser(User user) throws HandlerException {
        if(accountRepository.existsById(user.getId())) {
            throw new HandlerException("Пользователь с id " + user.getId() + " уже существует");
        }
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
