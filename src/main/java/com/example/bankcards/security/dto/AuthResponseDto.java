package com.example.bankcards.security.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private final String accessToken;
    private final String refreshToken;
}
