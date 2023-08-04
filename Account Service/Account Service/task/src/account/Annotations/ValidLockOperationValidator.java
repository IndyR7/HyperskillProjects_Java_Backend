package account.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidLockOperationValidator implements ConstraintValidator<ValidLockOperation, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidLockOperation constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String operation, ConstraintValidatorContext context) {
        return operation != null && operation.matches("LOCK|UNLOCK");
    }
}