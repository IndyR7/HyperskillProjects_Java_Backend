package engine.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import engine.Entities.User;
import engine.Repositories.UserRepository;
import engine.DTO.Requests.CreateUserRequest;

import javax.validation.Valid;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody CreateUserRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    protected User getCurrentAuthenticatedUser() {
        return userRepository.findByEmailIgnoreCase(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()).orElse(null);
    }
}