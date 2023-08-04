package account.Services;

import account.Constants.RoleType;
import account.Entities.Role;
import account.Entities.User;
import account.Exceptions.PasswordsMustBeDifferentException;
import account.Exceptions.UserExistException;
import account.Repositories.RoleRepository;
import account.Requests.CreateUserRequest;
import account.Responses.PasswordChangedResponse;
import account.Responses.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final UserService userService;
    private final LogService logService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationService(UserService userService, RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder, LogService logService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.logService = logService;
    }

    public ResponseEntity<UserDetailsResponse> registerUser(CreateUserRequest request) {
        if (userService.getUserByEmail(request.getEmail()) != null) {
            throw new UserExistException();
        }

        User user = new User();
        Role role = userService.getAllUsers().isEmpty() ?
                roleRepository.findByRoleType(RoleType.ADMINISTRATOR) :
                roleRepository.findByRoleType(RoleType.USER);

        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(role);
        user.setLocked(false);
        user.setAdmin(role.getRoleType().equals(RoleType.ADMINISTRATOR));

        userService.saveUser(user);

        logService.logCreateUser(user);

        return ResponseEntity.ok(new UserDetailsResponse(user));
    }

    public ResponseEntity<PasswordChangedResponse> changePassword(String newPassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new PasswordsMustBeDifferentException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userService.saveUser(user);

        logService.logChangePassword(user);

        return ResponseEntity.ok(new PasswordChangedResponse(user.getEmail()));
    }
}