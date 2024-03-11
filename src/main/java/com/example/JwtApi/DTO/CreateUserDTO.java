package com.example.JwtApi.DTO;

import com.example.JwtApi.Enums.RoleName;

public record CreateUserDTO(

        String email,
        String password,
        RoleName role
) {
}
