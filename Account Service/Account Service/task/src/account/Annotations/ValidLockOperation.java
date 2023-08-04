package account.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidLockOperationValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLockOperation {
    String message() default "Operation must match LOCK/UNLOCK!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
