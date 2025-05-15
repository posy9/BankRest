package com.example.bankcards.dto.userdtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserReadDto {

    @NotNull
    private Long id;

    private String username;
}
