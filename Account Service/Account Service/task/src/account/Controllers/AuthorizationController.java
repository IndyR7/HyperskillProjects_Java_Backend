package account.Controllers;

import account.Entities.User;
import account.Requests.PasswordChangeRequest;
import account.Responses.PasswordChangedResponse;
import account.Responses.UserDetailsResponse;
import account.Security.UserDetailsImpl;
import account.Services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    private final AuthorizationService accountService;

    public AuthorizationController(AuthorizationService userService) {
        this.accountService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserDetailsResponse> registerAccount(@Valid @RequestBody User user) {
        return accountService.registerUser(user);
    }

    @PostMapping("/changepass")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<PasswordChangedResponse> changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        if (userDetails != null) {
            return accountService.changePassword(userDetails, passwordChangeRequest.getNewPassword());
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}