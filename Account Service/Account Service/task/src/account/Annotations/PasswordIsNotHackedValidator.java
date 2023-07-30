package account.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordIsNotHackedValidator implements ConstraintValidator<PasswordIsNotHacked, String> {
    private static final List<String> BREACHED_PASSWORDS = Arrays.asList(
            "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
    );

    private String errorMessage;

    @Override
    public void initialize(PasswordIsNotHacked constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return !BREACHED_PASSWORDS.contains(password);
    }
}