package com.example.bankcards.config;

import com.example.bankcards.dto.carddtos.CardReadDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.converter.CardNumberMaskingConverter;
import com.example.bankcards.util.converter.ExpiryDateToLocalDateConverter;
import com.example.bankcards.util.converter.ExpiryDateToStringConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(CardNumberMaskingConverter cardNumberMaskingConverter,
                                   ExpiryDateToStringConverter expiryDateToStringConverter,
                                   ExpiryDateToLocalDateConverter expiryDateToLocalDateConverter) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Card.class, CardReadDto.class)
                .addMappings(mapper -> mapper
                        .using(cardNumberMaskingConverter)
                        .map(Card::getCardNumber, CardReadDto::setCardNumber));
        modelMapper.addConverter(expiryDateToStringConverter);
        modelMapper.addConverter(expiryDateToLocalDateConverter);
        return modelMapper;
    }
}
