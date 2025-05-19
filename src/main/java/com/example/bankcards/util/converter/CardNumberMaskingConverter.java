package com.example.bankcards.util.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class CardNumberMaskingConverter implements Converter<String, String> {

    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int NUMBER_OF_MASKED_DIGITS = 12;
    private static final String MASK = "************";

    @Override
    public String convert(MappingContext<String, String> context) {
        String cardNumber = context.getSource();
        if (cardNumber != null && cardNumber.length() == CARD_NUMBER_LENGTH) {
            return MASK + cardNumber.substring(NUMBER_OF_MASKED_DIGITS);
        }
        return cardNumber;

    }
}