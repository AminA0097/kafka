package com.UserService.User.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PassWordValidator implements ConstraintValidator<ValidPass,String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()){
            return false;
        }
        return value.matches(PASSWORD_PATTERN);
    }
}
