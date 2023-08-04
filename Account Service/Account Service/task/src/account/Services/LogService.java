package account.Services;

import account.Constants.LogType;
import account.Constants.Operation;
import account.Entities.Log;
import account.Entities.User;
import account.Exceptions.InvalidUserLockException;
import account.Repositories.LogRepository;
import account.Responses.LogDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {
    private LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logCreateUser(User user) {
        log(LogType.CREATE_USER, user.getEmail(), "/api/auth/signup");
    }

    public void logDeleteUser(User user) {
        log(LogType.DELETE_USER, user.getEmail(), "/api/admin/user");
    }

    public void logChangePassword(User user) {
        log(LogType.CHANGE_PASSWORD, user.getEmail(), "/api/auth/changepass");
    }

    public void logChangeLockStatus(Operation operation, User user, String... subjectOptional) {
        if (user.isAdmin()) {
            throw new InvalidUserLockException();
        }

        LogType logType = LogType.valueOf(operation.name() + "_USER");
        String object = logType.equals(LogType.LOCK_USER) ? "Lock user " + user.getEmail()
                : "Unlock user " + user.getEmail();
        String subject = subjectOptional.length == 0 ? null : subjectOptional[0];

        log(logType, object, "/api/admin/user/access", subject);
    }

    public void logRoleChange(Operation operation, String role, User user) {
        LogType logType = LogType.valueOf(operation.name() + "_ROLE");
        String object = logType.equals(LogType.GRANT_ROLE) ? "Grant role " + role + " to " + user.getEmail()
                : "Remove role " + role + " from " + user.getEmail();

        log(logType, object, "/api/admin/user/role");
    }

    public void logBruteForce(String subject, String path) {
        log(LogType.BRUTE_FORCE, path, path, subject);
    }

    public void logLoginFailed(String subject, String path) {
        log(LogType.LOGIN_FAILED, path, path, subject);
    }

    public void logAccessDenied(String path) {
        log(LogType.ACCESS_DENIED, path, path);
    }

    protected List<LogDetailsResponse> getLogs() {
        return logRepository.findAll().stream()
                .map(LogDetailsResponse::new)
                .toList();
    }

    private void log(LogType logType, String object, String path, String... subjectOptional) {
        Log log = new Log();

        LocalDateTime date = LocalDateTime.now();
        String subject = subjectOptional.length > 0 && subjectOptional[0] != null ? subjectOptional[0]
                : SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser") ? "Anonymous"
                : SecurityContextHolder.getContext().getAuthentication().getName();

        log.setValues(date, logType, subject, object, path);

        logRepository.save(log);
    }
}