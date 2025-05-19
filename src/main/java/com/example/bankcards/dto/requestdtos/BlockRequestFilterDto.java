package com.example.bankcards.dto.requestdtos;

import com.example.bankcards.dto.FilterDto;
import lombok.Data;

@Data
public class BlockRequestFilterDto implements FilterDto {

    private Long cardId;
}
