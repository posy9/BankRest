package com.example.bankcards.dto.requestdtos;

import com.example.bankcards.dto.CreateDto;
import com.example.bankcards.dto.carddtos.CardForBlockDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlockRequestCreateDto implements CreateDto {

    @Valid
    @NotNull(message = "should not be empty")
    private CardForBlockDto card;

    private String description;
}
