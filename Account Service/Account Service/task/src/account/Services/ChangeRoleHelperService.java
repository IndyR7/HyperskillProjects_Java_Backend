package account.Services;

import account.Constants.Operation;
import account.Constants.RoleType;
import account.Entities.Role;
import account.Entities.User;
import account.Exceptions.*;
import account.Repositories.RoleRepository;
import account.Requests.ChangeRoleRequest;
import account.Responses.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChangeRoleHelperService {
    private final UserService userService;
    private final LogService logService;
    private final RoleRepository roleRepository;

    @Autowired
    public ChangeRoleHelperService(UserService userService, RoleRepository roleRepository,
                                   LogService logService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.logService = logService;
    }

    public ResponseEntity<UserDetailsResponse> getResult(ChangeRoleRequest request) {
        User user = userService.getUserByEmail(request.getUser());

        if (user == null) {
            throw new UserNotFoundException();
        }

        RoleType targetRole = RoleType.valueOf(request.getRole());

        if (Operation.valueOf(request.getOperation()).equals(Operation.GRANT)) {
            validateGrantOperation(user, targetRole);
        } else {
            validateRemoveOperation(user, targetRole);
        }

        userService.saveUser(user);

        logService.logRoleChange(Operation.valueOf(request.getOperation()), request.getRole(), user);

        return ResponseEntity.ok(new UserDetailsResponse(user));
    }

    private void validateGrantOperation(User user, RoleType targetRole) {
        if (isInvalidRoleComboForGrantOperation(user, targetRole)) {
            throw new InvalidRoleComboException();
        }

        Role role = roleRepository.findByRoleType(targetRole);

        user.getRoles().add(role);
    }

    private void validateRemoveOperation(User user, RoleType targetRole) {
        if (!userContainsRole(user, targetRole)) {
            throw new UserDoesNotHaveRoleException();
        }

        if (isDeletingAdministrator(user, targetRole)) {
            throw new InvalidDeleteRequestException();
        }

        if (isOnlyRole(user)) {
            throw new UserMustHaveAtLeastOneRoleException();
        }

        user.getRoles().removeIf(role -> role.getRoleType().equals(targetRole));
    }

    private boolean userContainsRole(User user, RoleType targetRole) {
        return user.getRoles().stream()
                .map(Role::getRoleType)
                .anyMatch(roleType -> roleType.equals(targetRole));
    }

    private boolean isOnlyRole(User user) {
        return user.getRoles().size() == 1;
    }

    private boolean isDeletingAdministrator(User user, RoleType targetRole) {
        return user.isAdmin() && targetRole.equals(RoleType.ADMINISTRATOR);
    }

    private boolean isInvalidRoleComboForGrantOperation(User user, RoleType targetRole) {
        return targetRole.equals(RoleType.ADMINISTRATOR) || user.isAdmin();
    }
}