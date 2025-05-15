package com.example.bankcards.config;

import com.example.bankcards.dto.carddtos.CardReadDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.masking.CardNumberMaskingConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(CardNumberMaskingConverter cardNumberMaskingConverter) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Card.class, CardReadDto.class)
                .addMappings(mapper -> mapper
                        .using(cardNumberMaskingConverter)
                        .map(Card::getCardNumber, CardReadDto::setCardNumber));

        return modelMapper;
    }
}
