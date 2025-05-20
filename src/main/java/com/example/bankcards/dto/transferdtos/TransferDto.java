package com.example.bankcards.dto.transferdtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    @Positive
    @NotNull(message = "should not be empty")
    private Long toCardId;

    @Positive(message = "should be more than 0")
    @NotNull(message = "should not be empty")
    private BigDecimal amount;
}
