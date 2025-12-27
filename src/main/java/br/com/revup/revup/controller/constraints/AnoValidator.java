package br.com.revup.revup.controller.constraints;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnoValidator implements ConstraintValidator<AnoFabricacao, Integer> {

    private static final int ANO_MIN = 1930;
    private static final int ANO_MAX = Year.now().getValue();

    @Override
    public boolean isValid(Integer ano, ConstraintValidatorContext context) {
        return (ANO_MIN <= ano && ano <= ANO_MAX);
    }
    
}
