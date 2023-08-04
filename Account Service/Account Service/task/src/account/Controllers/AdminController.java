package account.Controllers;

import account.Requests.ChangeLockStatusRequest;
import account.Requests.ChangeRoleRequest;
import account.Responses.UserDeletedResponse;
import account.Responses.UserDetailsResponse;
import account.Responses.UserLockOperationResponse;
import account.Services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user/**")
    public ResponseEntity<List<UserDetailsResponse>> getUserDetails() {
        return adminService.getUserDetails();
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<UserDeletedResponse> deleteUser(@PathVariable String email) {
        return adminService.deleteUser(email);
    }

    @PutMapping("/user/role/**")
    public ResponseEntity<UserDetailsResponse> changeUserRole(@Valid @RequestBody ChangeRoleRequest request) {
        return adminService.changeUserRole(request);
    }

    @PutMapping("/user/access/**")
    public ResponseEntity<UserLockOperationResponse> changeUserLockStatus(
            @Valid @RequestBody ChangeLockStatusRequest request) {
        return adminService.changeUserLockStatus(request);
    }
}