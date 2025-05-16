package com.example.bankcards.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class ExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, String> {

    private final DateTimeFormatter formatter;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            return false;
        }

        try {
            YearMonth expiry = YearMonth.parse(value, formatter);
            YearMonth now = YearMonth.now();

            return !expiry.isBefore(now); // карта не просрочена
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}