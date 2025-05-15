package com.example.bankcards.dto.userdtos;

import com.example.bankcards.dto.ReadDto;
import com.example.bankcards.dto.roledtos.RoleReadDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserReadDto implements ReadDto {

    @NotNull(message = "should not be null")
    private Long id;

    private String username;

    private RoleReadDto role;
}
