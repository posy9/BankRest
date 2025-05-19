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
        if (value == null) {
            return false;
        }
        try {
            YearMonth expiry = YearMonth.parse(value, formatter);
            YearMonth now = YearMonth.now();

            return !expiry.isBefore(now);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
