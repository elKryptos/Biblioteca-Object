package it.objectmethod.biblioteca.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BiblioRegexpValidator implements ConstraintValidator<BiblioRegexpValidation, String> {

    private String regexp;
    private String message;
    private boolean allowNull;

    @Override
    public void initialize(BiblioRegexpValidation constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
        this.message = constraintAnnotation.message();
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the value is null or empty
        if (!this.allowNull && (value == null || value.isEmpty())) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
            return false;
        } else if (this.allowNull && (value == null || value.isEmpty())) {
            return true; // Null value is allowed
        }

        // Verify regex
        boolean valid = value.matches(this.regexp);
        if (!valid) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
        }
        return valid;
    }

}
