package com.local.orderhandler.service;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(User user) throws HandlerException{
        if (userRepository.existsById(user.getId())){
            throw new HandlerException("Пользователь с id " + user.getId() +" уже существует");
        }
        userRepository.save(user);
    }
    public User getUserByName (String name){
        return userRepository.getUserByName(name);
    }
    public List<User> getAllUsers () throws HandlerException{
        List<User> userList = (List<User>) userRepository.findAll();
        if(userList.isEmpty()) throw new HandlerException("Список пользователей пуст");
        return userList;
    }
    public void deleteUser(int id) throws HandlerException{
        if (!userRepository.existsById(id)){
            throw new HandlerException("Пользователь с id " + id +" не существует");
        }
        userRepository.delete(userRepository.findById(id).isPresent().get());
    }
    public void update (User user) throws HandlerException{
        if (!userRepository.existsById(user.getId())){
            throw new HandlerException("Пользователь с id " + user.getId() +" не существует");
        }
        userRepository.save(user);

    }
}
