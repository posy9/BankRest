package com.example.bankcards.dto.requestdtos;

import com.example.bankcards.dto.ReadDto;
import com.example.bankcards.dto.carddtos.CardForBlockDto;
import lombok.Data;

@Data
public class BlockRequestReadDto implements ReadDto {

    private Long id;

    private CardForBlockDto card;

    private String description;
}
