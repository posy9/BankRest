package com.example.bankcards.dto.carddtos;

import com.example.bankcards.dto.statusdtos.StatusReadDto;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardReadDto {

    private Long id;

    private String cardNumber;

    private String expiryDate;

    private BigDecimal balance;

    private User user;

    private StatusReadDto status;
}
