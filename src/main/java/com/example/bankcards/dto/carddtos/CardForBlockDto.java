package com.example.bankcards.dto.carddtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardForBlockDto {

    @PositiveOrZero
    private Long id;
}
