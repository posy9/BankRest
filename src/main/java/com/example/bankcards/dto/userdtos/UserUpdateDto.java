package com.example.bankcards.dto.userdtos;

import com.example.bankcards.dto.UpdateDto;
import com.example.bankcards.dto.roledtos.RoleReadDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDto implements UpdateDto {

    @NotBlank(message = "should not be empty")
    private String username;

    @NotBlank(message = "should not be empty")
    private String password;

    @NotNull(message = "should not be empty")
    private RoleReadDto role;
}
