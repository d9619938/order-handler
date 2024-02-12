/*
package com.local.orderhandler.controller;

import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> addUser(@RequestBody @Valid User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok().build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    //    @GetMapping ("/get/{id}")
//    public User getUser (@PathVariable int id) {
//
//    }
    @GetMapping("/get")
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //    @DeleteMapping ("/del/{id}")
//    public ResponseEntity<Void> deleteUser (@PathVariable int id) {
//        try {
//            userService.deleteUser(id);
//        } catch (HandlerException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }
    @PutMapping
    private ResponseEntity<Void> update(@RequestBody @Valid User user) {
        try {
            userService.update(user);
            return ResponseEntity.ok().build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}*/
