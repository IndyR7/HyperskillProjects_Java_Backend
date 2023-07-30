package account.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User-Period combo not found!")
public class UserPeriodComboNotFoundException extends RuntimeException {
    public UserPeriodComboNotFoundException() {
        super();
    }
}