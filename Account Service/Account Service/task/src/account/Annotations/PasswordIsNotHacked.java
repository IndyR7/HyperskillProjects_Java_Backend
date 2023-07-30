package account.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordIsNotHackedValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordIsNotHacked {
    String message() default "The password is in the hacker's database!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}