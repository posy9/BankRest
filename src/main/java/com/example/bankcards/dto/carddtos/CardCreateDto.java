package com.example.bankcards.dto.carddtos;

import com.example.bankcards.dto.CreateDto;
import com.example.bankcards.dto.userdtos.UserReadDto;
import com.example.bankcards.util.validation.ValidCardNumber;
import com.example.bankcards.util.validation.ValidExpiryDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardCreateDto implements CreateDto {


    @ValidCardNumber
    private String cardNumber;

    @ValidExpiryDate
    private String expiryDate;

    @PositiveOrZero
    private BigDecimal balance;

    @Valid
    private UserReadDto user;
}
