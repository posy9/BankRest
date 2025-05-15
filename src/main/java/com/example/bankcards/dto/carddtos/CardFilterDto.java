package com.example.bankcards.dto.carddtos;

import lombok.Data;

@Data
public class CardFilterDto {

     private Long user_id;

     private Long status_id;

     private String expiryDate;

     private String cardNumber;
}
