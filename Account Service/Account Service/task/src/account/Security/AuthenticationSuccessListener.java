package account.Security;

import account.Entities.User;
import account.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private UserService userService;

    @Autowired
    public AuthenticationSuccessListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String email = event.getAuthentication().getName();
        User user = userService.getUserByEmail(email);

        if (user.getFailedLoginAttempts() > 0) {
            userService.resetFailedAttempts(user);
        }
    }
}