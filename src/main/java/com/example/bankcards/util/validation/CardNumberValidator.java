package com.example.bankcards.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<ValidCardNumber, String> {

    @Override
    public  boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        // Удаляем пробелы и проверяем формат
        if(number == null) {
            return false;
        }
        number = number.replaceAll("\\s", "");

        // Проверяем, что точно 16 цифр
        if (!number.matches("\\d{16}")) {
            return false;
        }

        return passesLuhnCheck(number);
    }

    private boolean passesLuhnCheck(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }
}
