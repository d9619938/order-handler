package com.local.orderhandler.dto;


import com.local.orderhandler.entity.Role;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private int id;
    private String username;
    private String email;
    private Role role;
}
