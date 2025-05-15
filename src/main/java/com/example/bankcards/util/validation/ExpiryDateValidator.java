package com.example.bankcards.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            return false;
        }

        try {
            YearMonth expiry = YearMonth.parse(value, FORMATTER);
            YearMonth now = YearMonth.now();

            return !expiry.isBefore(now); // карта не просрочена
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}