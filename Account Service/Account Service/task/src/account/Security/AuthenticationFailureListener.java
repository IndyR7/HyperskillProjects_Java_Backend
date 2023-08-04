package account.Security;

import account.Constants.Operation;
import account.Entities.User;
import account.Services.LogService;
import account.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private HttpServletRequest request;
    private final LogService logService;
    private final UserService userService;


    @Autowired
    public AuthenticationFailureListener(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = event.getAuthentication().getName();
        User user = userService.getUserByEmail(email);

        logService.logLoginFailed(email, request.getRequestURI());

        if (user == null || user.isAdmin()) {
            return;
        }

        if (user.getFailedLoginAttempts() >= UserService.MAX_FAILED_ATTEMPTS - 1) {
            logService.logBruteForce(user.getEmail(), request.getRequestURI());
            logService.logChangeLockStatus(Operation.LOCK, user, email);

            userService.lock(user);
        }

        userService.increaseFailedAttempts(user);
    }
}
