package account.Requests;

import account.Annotations.PasswordIsNotHacked;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeRequest {
    @JsonProperty("new_password")
    @NotEmpty
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @PasswordIsNotHacked
    private String newPassword;
}