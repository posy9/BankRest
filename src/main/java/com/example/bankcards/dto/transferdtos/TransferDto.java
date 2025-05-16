package com.example.bankcards.dto.transferdtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    @PositiveOrZero
    private Long toCardId;

    @PositiveOrZero
    private BigDecimal amount;
}
