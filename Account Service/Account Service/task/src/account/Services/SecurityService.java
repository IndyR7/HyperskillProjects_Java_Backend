package account.Services;

import account.Responses.LogDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    private final LogService logService;

    @Autowired
    public SecurityService(LogService logService) {
        this.logService = logService;
    }

    public ResponseEntity<List<LogDetailsResponse>> getLogs() {
        return ResponseEntity.ok(logService.getLogs());
    }
}