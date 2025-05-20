package com.example.bankcards.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

    @NotBlank(message = "should not be empty")
    private String username;

    @NotBlank(message = "should not be empty")
    private String password;
}
