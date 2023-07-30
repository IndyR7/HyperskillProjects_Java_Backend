package account.Services;

import account.Constants.RoleType;
import account.Entities.Role;
import account.Entities.User;
import account.Exceptions.*;
import account.Repositories.RoleRepository;
import account.Repositories.UserRepository;
import account.Requests.ChangeRoleRequest;
import account.Responses.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeRoleHelperService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ChangeRoleRequest request;
    private User user;

    @Autowired
    public ChangeRoleHelperService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<UserDetailsResponse> getResult(ChangeRoleRequest request) {
        this.request = request;
        this.user = userRepository.findByEmailIgnoreCase(request.getUser());

        if (this.user == null) {
            throw new UserNotFoundException();
        }

        if (request.getOperation().equals("GRANT")) {
            if (isInvalidRoleCombo()) {
                throw new InvalidRoleComboException();
            }

            Role role = new Role();
            role.setRoleType(RoleType.valueOf(request.getRole()));

            roleRepository.save(role);
            user.getRoles().add(role);
        } else {
            if (!userContainsRole()) {
                throw new UserDoesNotHaveRoleException();
            }

            if (isDeletingAdministrator()) {
                throw new InvalidDeleteRequestException();
            }

            if (isOnlyRole()) {
                throw new UserMustHaveAtLeastOneRoleException();
            }

            for (Role role : user.getRoles()) {
                if (role.getRoleType().equals(RoleType.valueOf(request.getRole()))) {
                    user.getRoles().remove(role);
                    break;
                }
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok(new UserDetailsResponse(user));
    }

    private boolean userContainsRole() {
        List<RoleType> userRoles = user.getRoles().stream()
                .map(Role::getRoleType)
                .toList();

        return userRoles.contains(RoleType.valueOf(request.getRole()));
    }

    private boolean isOnlyRole() {
        return user.getRoles().size() == 1;
    }

    private boolean isDeletingAdministrator() {
        return user.getRoles().stream()
                .map(Role::getRoleType)
                .toList()
                .contains(RoleType.ADMINISTRATOR)
                && RoleType.valueOf(request.getRole()).equals(RoleType.ADMINISTRATOR);
    }

    private boolean isInvalidRoleCombo() {
        List<RoleType> userRoles = user.getRoles().stream()
                .map(Role::getRoleType)
                .toList();

        return RoleType.valueOf(request.getRole()).equals(RoleType.ADMINISTRATOR)
                || userRoles.contains(RoleType.ADMINISTRATOR);
    }
}