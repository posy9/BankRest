package com.example.bankcards.dto.roledtos;

import com.example.bankcards.dto.ReadDto;
import lombok.Data;

@Data
public class RoleReadDto implements ReadDto {

    private Long id;

    private String name;
}
