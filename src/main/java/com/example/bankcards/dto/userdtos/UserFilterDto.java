package com.example.bankcards.dto.userdtos;

import com.example.bankcards.dto.FilterDto;
import lombok.Data;

@Data
public class UserFilterDto implements FilterDto {

    private String username;

    private Long role_id;
}
