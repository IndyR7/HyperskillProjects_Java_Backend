package account.Responses;

import lombok.Data;

@Data
public class UserLockOperationResponse {
    private String status;

    public UserLockOperationResponse(String user, String operation) {
        this.status = String.format("User %s %s!", user, operation.equals("LOCK") ? "locked" : "unlocked");
    }
}