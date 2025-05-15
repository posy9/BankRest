package com.example.bankcards.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ExpiryDateValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {
    String message() default "Некорректный срок действия карты (MM/YY) или карта уже просрочена";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}