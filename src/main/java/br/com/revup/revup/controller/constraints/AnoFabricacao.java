package br.com.revup.revup.controller.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AnoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoFabricacao {

    String message() default 
        "Ano de fabricação deve estar entre 1930 e o ano atual";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
