package account.Requests;

import account.Annotations.ValidLockOperation;
import lombok.Data;

@Data
public class ChangeLockStatusRequest {
    private String user;

    @ValidLockOperation
    private String operation;
}
