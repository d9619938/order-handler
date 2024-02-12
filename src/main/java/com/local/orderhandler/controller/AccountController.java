package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Role;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/account") //account/login
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String getRegistrationForm(User user){
        return "registrationForm";
    }
    @PostMapping("/registration")
    public String addNewUser(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "registrationForm";
        setupDefaultRole(user);
        try {
            accountService.saveUser(user);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:account/login";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private void setupDefaultRole(User user){
        Role def = new Role();
        def.setRoleType(Role.RoleType.ROLE_BUYER);
        user.setRole(def);
    }

    @PutMapping("/update")
    private ResponseEntity<Void> update(@RequestBody @Valid User user){
        try {
            accountService.update(user);
            return ResponseEntity.ok().build();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        try {
            return accountService.getAllUsers();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/get")
    public User getUserById(@RequestParam int id) {
        try {
            return accountService.getUserById(id);
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/del")
    public ResponseEntity<Void> deleteUser(@RequestParam int id){
        try{
            accountService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
