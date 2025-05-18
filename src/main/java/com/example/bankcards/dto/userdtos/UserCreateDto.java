package com.example.bankcards.dto.userdtos;

import com.example.bankcards.dto.CreateDto;
import com.example.bankcards.dto.ReadDto;
import com.example.bankcards.dto.roledtos.RoleReadDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto implements ReadDto, CreateDto {

    @NotBlank(message = "should not be empty")
    private String username;

    @NotBlank(message = "should not be empty")
    private String password;

    @NotNull(message = "should not be empty")
    private RoleReadDto role;
}
