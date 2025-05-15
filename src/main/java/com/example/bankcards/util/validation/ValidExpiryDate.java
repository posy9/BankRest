package com.example.bankcards.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ExpiryDateValidator.class)
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {
    String message() default "Invalid expiry date or card is already expired";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}