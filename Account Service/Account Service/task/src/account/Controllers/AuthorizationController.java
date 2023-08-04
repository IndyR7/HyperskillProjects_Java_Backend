package account.Controllers;

import account.Requests.CreateUserRequest;
import account.Requests.PasswordChangeRequest;
import account.Responses.PasswordChangedResponse;
import account.Responses.UserDetailsResponse;
import account.Services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/signup/**")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserDetailsResponse> registerUser(@Valid @RequestBody CreateUserRequest request) {
        return authorizationService.registerUser(request);
    }

    @PostMapping("/changepass/**")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<PasswordChangedResponse> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return authorizationService.changePassword(passwordChangeRequest.getNewPassword());
    }
}