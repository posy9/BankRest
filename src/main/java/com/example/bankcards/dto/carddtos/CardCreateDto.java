package com.example.bankcards.dto.carddtos;

import com.example.bankcards.dto.userdtos.UserReadDto;
import com.example.bankcards.util.validation.ValidCardNumber;
import com.example.bankcards.util.validation.ValidExpiryDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardCreateDto {


    @NotBlank
    @ValidCardNumber
    private String cardNumber;

    @NotBlank
    @ValidExpiryDate
    private String expiryDate;

    @PositiveOrZero
    private BigDecimal balance;

    @Valid
    private UserReadDto user;
}
