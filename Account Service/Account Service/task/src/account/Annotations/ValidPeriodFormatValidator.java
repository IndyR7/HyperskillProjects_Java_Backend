package account.Annotations;

import account.Constraints.FormattingConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPeriodFormatValidator implements ConstraintValidator<ValidPeriodFormat, String> {

    private String errorMessage;

    @Override
    public void initialize(ValidPeriodFormat constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String period, ConstraintValidatorContext context) {
        return period == null || FormattingConstraints.isValidPeriod(period);
    }
}