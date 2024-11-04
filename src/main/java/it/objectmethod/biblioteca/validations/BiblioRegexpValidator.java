package it.objectmethod.biblioteca.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BiblioRegexpValidator implements ConstraintValidator<BiblioRegexpValidation, String> {

    private String regexp;
    private String message;
    private boolean allowNull;
    private int annoPubblicazione;

    @Override
    public void initialize(BiblioRegexpValidation constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
        this.message = constraintAnnotation.message();
        this.allowNull = constraintAnnotation.allowNull();
        this.annoPubblicazione = constraintAnnotation.annoPubblicazione();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!this.allowNull && (value == null || value.isEmpty())) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
            return false;
        } else if (this.allowNull && (value == null || value.isEmpty())) {
            return true; // Null value is allowed
        }

        boolean valid = value.matches(this.regexp);
        if (!valid) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
            return false;
        }

        String annoStr = String.valueOf(this.annoPubblicazione);
        if (!value.contains(annoStr)) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}



//
//    public boolean isValidIsnb(String isnb, int annoPubblicazione, ConstraintValidatorContext context) {
//        if (isnb == null || isnb.isEmpty()) {
//            return true;
//        }
//
//        String annoStr = String.valueOf(annoPubblicazione);
//        if (!isnb.contains(annoStr)) {
//            context.buildConstraintViolationWithTemplate(this.message)
//                    .addConstraintViolation();
//            return false;
//        }
//        return true;
//    }

