package account.Services;

import account.Constants.Operation;
import account.Entities.User;
import account.Exceptions.InvalidDeleteRequestException;
import account.Exceptions.InvalidUserLockException;
import account.Exceptions.UserNotFoundException;
import account.Requests.ChangeLockStatusRequest;
import account.Requests.ChangeRoleRequest;
import account.Responses.UserDeletedResponse;
import account.Responses.UserDetailsResponse;
import account.Responses.UserLockOperationResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserService userService;
    private final ChangeRoleHelperService changeRoleHelperService;
    private final LogService logService;

    @Autowired
    public AdminService(UserService userService,
                        ChangeRoleHelperService changeRoleHelperService,
                        LogService logService) {
        this.userService = userService;
        this.changeRoleHelperService = changeRoleHelperService;
        this.logService = logService;
    }

    public ResponseEntity<List<UserDetailsResponse>> getUserDetails() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users.stream()
                .map(UserDetailsResponse::new)
                .toList());
    }

    @Transactional
    public ResponseEntity<UserDeletedResponse> deleteUser(String email) {
        if (!userService.userExists(email)) {
            throw new UserNotFoundException();
        }

        User user = userService.getUserByEmail(email);

        if (user.isAdmin()) {
            throw new InvalidDeleteRequestException();
        }

        userService.deleteUser(user);

        logService.logDeleteUser(user);

        return ResponseEntity.ok(new UserDeletedResponse(email));
    }

    @Transactional
    public ResponseEntity<UserDetailsResponse> changeUserRole(ChangeRoleRequest request) {
        return changeRoleHelperService.getResult(request);
    }

    public ResponseEntity<UserLockOperationResponse> changeUserLockStatus(ChangeLockStatusRequest request) {
        if (!userService.userExists(request.getUser())) {
            throw new UserNotFoundException();
        }

        User user = userService.getUserByEmail(request.getUser());

        if (user.isAdmin()) {
            throw new InvalidUserLockException();
        }

        if (Operation.valueOf(request.getOperation()).equals(Operation.UNLOCK)) {
            user.setFailedLoginAttempts(0);
        }

        user.setLocked(Operation.valueOf(request.getOperation()).equals(Operation.LOCK));

        userService.saveUser(user);

        logService.logChangeLockStatus(Operation.valueOf(request.getOperation()), user);

        return ResponseEntity.ok(new UserLockOperationResponse(user.getEmail(), request.getOperation()));
    }
}