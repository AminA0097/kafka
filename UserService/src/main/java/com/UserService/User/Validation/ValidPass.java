package com.UserService.User.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PassWordValidator.class)
public @interface ValidPass {
    String message() default "Invalid password!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
