package account.Annotations;

import account.Constants.RoleType;
import account.Exceptions.RoleNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        try {
            RoleType.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException();
        }
    }
}