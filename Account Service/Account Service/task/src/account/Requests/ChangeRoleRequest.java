package account.Requests;

import account.Annotations.ValidChangeRoleOperation;
import account.Annotations.ValidRole;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    private String user;

    @ValidRole
    private String role;

    @ValidChangeRoleOperation
    private String operation;
}