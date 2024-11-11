package it.objectmethod.biblioteca.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BiblioRegexpValidator.class)
public @interface BiblioRegexpValidation {
    boolean allowNull() default false;
    String regexp();
    String message();
    //int annoPubblicazione();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
