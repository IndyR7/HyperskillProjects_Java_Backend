package account.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User account is locked")
public class UserLockedException extends RuntimeException {
    public UserLockedException() {
        super();
    }
}