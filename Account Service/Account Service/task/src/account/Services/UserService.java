package account.Services;

import account.Entities.User;
import account.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public static final int MAX_FAILED_ATTEMPTS = 5;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void increaseFailedAttempts(User user) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

        userRepository.save(user);
    }

    public void resetFailedAttempts(User user) {
        user.setFailedLoginAttempts(0);

        userRepository.save(user);
    }

    public void lock(User user) {
        user.setLocked(true);

        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
