package com.example.bankcards.dto.requestdtos;

import com.example.bankcards.dto.UpdateDto;
import com.example.bankcards.dto.carddtos.CardForBlockDto;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class BlockRequestUpdateDto implements UpdateDto {

    @Valid
    private CardForBlockDto card;

    private String description;
}
