package com.example.bankcards.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshRequestDto {

    @NotBlank(message = "should not be empty")
    private String refreshToken;
}
