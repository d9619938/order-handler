package com.local.orderhandler.service;

import com.local.orderhandler.entity.User;
import com.local.orderhandler.repository.RoleRepository;
import com.local.orderhandler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SecurityService implements UserDetailsService { // класс используется библиотекой, когда пользователь введет данные в форму

    private final UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Пользователь с именем " + username + " не найден."));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleType().name()); // для управления ролями
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Set.of(authority)); // если пользователь прошел авторизацию, то он попадает в контекст
    }
}
