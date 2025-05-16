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
public class ExpiryDateToLocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter;

    @Override
    public LocalDate convert(MappingContext<String, LocalDate> context) {
        String source = context.getSource();
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            YearMonth ym = YearMonth.parse(source, formatter);
            return ym.atEndOfMonth();
        } catch (Exception e) {
            return null;
        }
    }
}
