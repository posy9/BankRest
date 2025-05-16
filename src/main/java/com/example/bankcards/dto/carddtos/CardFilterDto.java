package com.example.bankcards.dto.carddtos;

import com.example.bankcards.dto.FilterDto;
import lombok.Data;

@Data
public class CardFilterDto implements FilterDto {

    private Long user_id;

    private Long status_id;

    private String expiryDate;

    private String cardNumber;
}
