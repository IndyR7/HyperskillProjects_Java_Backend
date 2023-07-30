package account.Responses;

import lombok.Data;

@Data
public class PasswordChangedResponse {
    private String email;
    private String status;

    public PasswordChangedResponse(String email) {
        this.email = email;
        this.status = "The password has been updated successfully";
    }
}