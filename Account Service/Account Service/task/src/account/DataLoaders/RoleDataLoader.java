package account.DataLoaders;

import account.Constants.RoleType;
import account.Entities.Role;
import account.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        createRoles();
    }

    private void createRoles() {
        if (!roleRepository.findAll().isEmpty()) {
            return;
        }

        for (RoleType roleType : RoleType.values()) {
            Role role = new Role();

            role.setRoleType(roleType);
            roleRepository.save(role);
        }
    }
}