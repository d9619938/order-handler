package com.local.orderhandler.configuration;

import com.local.orderhandler.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // помещаем в контекст
@EnableWebSecurity // настройки Security оп умолчанию будут изменены
@EnableMethodSecurity(securedEnabled = true) // позволит над методами контроллеров указать доступ по ролям
public class SpringSecurityConfiguration {
    private final SecurityService securityService;

    @Autowired
    public SpringSecurityConfiguration(SecurityService securityService) {
        this.securityService = securityService;
    }

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(); // стандартный кодировщик для шифрования
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.csrf(AbstractHttpConfigurer::disable) // отмена проверки токенов
//               .securityMatcher("/account/**")
               .authenticationProvider(authenticationProvider()) // атентификация будет производиться нашим AuthenticationProvider
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/account/registration", "account/login")
                        .permitAll()//запросы доступны всем
                        .requestMatchers(HttpMethod.DELETE)
                        .hasRole("ADMIN")//запросы доступны только указанным ролям
                        .anyRequest()
                        .authenticated()
                        )
                        .formLogin(form -> form
                                .usernameParameter("username") // значение атрибута name в html форме
                                .passwordParameter("password") // значение атрибута name в html форме
                                .loginPage("/account/login") // форма доступна по адресу
                                .loginProcessingUrl("/account/login") // обработчик, значение атрибута action тега form
                                .failureUrl("/account/login?failed") // ошибка авторизации - редирект на /account/login с параметром failed
                                .defaultSuccessUrl("/account") // перенаправление после успешной авторизации
                                .permitAll()
                        )
                        .logout(logout -> logout.logoutUrl("/account/logout") // <a th:href="@{/account/logout}">Выйти</a>
                                .logoutSuccessUrl("/account/login") // перенаправление после /account/logout
                                .permitAll())  // [ВЫЙТИ] /account/logout
                        .build();
    }
}
