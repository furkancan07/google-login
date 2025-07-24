package com.rf.spring_oauth2.validation;

import com.rf.spring_oauth2.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValid implements ConstraintValidator<UniqueEmail, String> {
    private final UserService service;

    public UniqueEmailValid(UserService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !service.existByEmail(s);
    }
}
