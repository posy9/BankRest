package com.example.bankcards.dto.carddtos;

import com.example.bankcards.dto.statusdtos.StatusReadDto;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CardPatchDto {

    @Valid
    private StatusReadDto status;
}
