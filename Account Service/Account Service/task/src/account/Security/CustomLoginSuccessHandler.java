package account.Security;

import account.Entities.User;
import account.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
    public CustomLoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        if (user.getFailedLoginAttempts() > 0) {
            userService.resetFailedAttempts(user);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}