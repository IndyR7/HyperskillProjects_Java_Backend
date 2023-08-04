package account.Controllers;

import account.Responses.LogDetailsResponse;
import account.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {
    private SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/events/**")
    public ResponseEntity<List<LogDetailsResponse>> getLogs() {
        return securityService.getLogs();
    }
}
