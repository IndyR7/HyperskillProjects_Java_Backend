package account.Requests;

import account.Annotations.PasswordIsNotHacked;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Pattern(regexp = ".+@acme\\.com")
    private String email;

    @NotEmpty
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @PasswordIsNotHacked
    private String password;
}
