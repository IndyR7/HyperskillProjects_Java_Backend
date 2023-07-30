package account.Services;

import account.Constants.RoleType;
import account.Entities.Role;
import account.Entities.User;
import account.Exceptions.InvalidDeleteRequestException;
import account.Exceptions.UserNotFoundException;
import account.Repositories.UserRepository;
import account.Requests.ChangeRoleRequest;
import account.Responses.UserDeletedResponse;
import account.Responses.UserDetailsResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private UserRepository userRepository;

    private ChangeRoleHelperService changeRoleHelperService;

    @Autowired
    public AdminService(UserRepository userRepository, ChangeRoleHelperService changeRoleHelperService) {
        this.userRepository = userRepository;
        this.changeRoleHelperService = changeRoleHelperService;
    }

    public ResponseEntity<List<UserDetailsResponse>> getUserDetails() {
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(users.stream()
                .map(UserDetailsResponse::new)
                .toList());
    }

    @Transactional
    public ResponseEntity<UserDeletedResponse> deleteUser(String email) {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findByEmailIgnoreCase(email);

        if (user.getRoles().stream()
                .map(Role::getRoleType)
                .toList()
                .contains(RoleType.ADMINISTRATOR)) {
            throw new InvalidDeleteRequestException();
        }

        userRepository.delete(user);

        return ResponseEntity.ok(new UserDeletedResponse(email));
    }

    @Transactional
    public ResponseEntity<UserDetailsResponse> changeUserRole(ChangeRoleRequest request) {
        return changeRoleHelperService.getResult(request);
    }
}