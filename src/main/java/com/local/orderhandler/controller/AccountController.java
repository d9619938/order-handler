package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Role;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
        if (isAuthenticated()) return "redirect:/account";
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String addNewUserHTML(@Valid User user, BindingResult bindingResult){ // РАБОТАЕТ
        if(bindingResult.hasErrors()) return "registrationForm";
        try {
            accountService.saveUser(user);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:/account/login";
    }
    @GetMapping("login")
    public String login() {
        if (isAuthenticated()) return "redirect:/account";
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

    @GetMapping
    public String account(){
        return "account";
    }


    // можно заменить на Filter
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class. isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
    @GetMapping("/development")
    public String development(){
        return "development";
    }
    @GetMapping("/home")
    public String home(){
        return "home";
    }

//    @GetMapping("/profile")
//    public String getProfileUser (User user, Principal principal){
//        if (!isAuthenticated()) return "login";
//
//        User
//    }
}
