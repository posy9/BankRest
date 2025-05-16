package com.example.bankcards.dto.requestdtos;

import com.example.bankcards.dto.CreateDto;
import com.example.bankcards.dto.carddtos.CardForBlockDto;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class BlockRequestCreateDto implements CreateDto {

    @Valid
    private CardForBlockDto card;

    private String description;
}
