package org.example.userservice.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxBorrowLimitValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxBorrowLimit {
    String message() default "User cannot borrow more than 5 books";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
