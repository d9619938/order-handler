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
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/registration") // РАБОТАЕТ
    public String getRegistrationFormHTML(User user){ // РАБОТАЕТ
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String addNewUserHTML(@Valid User user, BindingResult bindingResult){ // РАБОТАЕТ
        if(bindingResult.hasErrors()) return "registrationForm";
//       setupDefaultRole(user);
        try {
            accountService.saveUser(user);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:login";
    }
    @GetMapping("login")
    public String login() {
        return "login";
    }

    private void setupDefaultRole(User user){
        Role def = new Role();
        def.setRoleType(Role.RoleType.ROLE_BUYER);
        user.setRole(def);
    }
    @ResponseBody
    @PutMapping("/update")
    private ResponseEntity<Void> update(@RequestBody @Valid User user){  // НЕ РАБОТАЕТ ?
        try {
            accountService.update(user);
            return ResponseEntity.ok().build();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/getAll")
    public List<User> getAllUsersJSON(){ // РАБОТАЕТ
        try {
            return accountService.getAllUsers();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ResponseBody
    @GetMapping("/get")
    public User getUserById(@RequestParam int id) { // РАБОТАЕТ
        try {
            return accountService.getUserById(id);
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/del") // РАБОТАЕТ
    public ResponseEntity<Void> deleteUser(@RequestParam int id){
        try{
            accountService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (HandlerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
