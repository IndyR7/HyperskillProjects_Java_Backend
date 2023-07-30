package account.Services;

import account.Constants.RoleType;
import account.Entities.Role;
import account.Entities.User;
import account.Exceptions.PasswordsMustBeDifferentException;
import account.Exceptions.UserExistException;
import account.Repositories.RoleRepository;
import account.Repositories.UserRepository;
import account.Responses.PasswordChangedResponse;
import account.Responses.UserDetailsResponse;
import account.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<UserDetailsResponse> registerUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserExistException();
        }

        Role role = new Role();
        role.setRoleType(userRepository.findAll().isEmpty() ? RoleType.ADMINISTRATOR : RoleType.USER);

        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);

        roleRepository.save(role);
        userRepository.save(user);

        return ResponseEntity.ok(new UserDetailsResponse(user));
    }

    public ResponseEntity<PasswordChangedResponse> changePassword(UserDetailsImpl userDetails, String newPassword) {
        User user = userRepository.findByEmailIgnoreCase(userDetails.getUsername());

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new PasswordsMustBeDifferentException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(new PasswordChangedResponse(user.getEmail()));
    }
}