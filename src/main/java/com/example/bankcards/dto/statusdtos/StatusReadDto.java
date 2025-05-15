package com.example.bankcards.dto.statusdtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusReadDto {

    @NotNull(message = "should not be null")
    private Long id;

    private String name;
}
