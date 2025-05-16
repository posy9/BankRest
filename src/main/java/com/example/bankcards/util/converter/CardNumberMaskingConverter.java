package com.example.bankcards.util.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class CardNumberMaskingConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
        String cardNumber = context.getSource();
        if (cardNumber != null && cardNumber.length() == 16) {
            return "************" + cardNumber.substring(12);
        }
        return cardNumber;

    }
}