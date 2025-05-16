package com.example.bankcards.util.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ExpiryDateToStringConverter implements Converter<LocalDate, String> {

    private final DateTimeFormatter formatter;

    @Override
    public String convert(MappingContext<LocalDate, String> context) {
        LocalDate source = context.getSource();
        if (source == null) {
            return null;
        }
        YearMonth ym = YearMonth.from(source);
        return ym.format(formatter);
    }
}

