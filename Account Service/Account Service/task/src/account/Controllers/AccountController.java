package account.Controllers;

import account.Requests.PaymentRequest;
import account.Responses.PaymentsSavedResponse;
import account.Services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acct")
@Validated
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentsSavedResponse> registerPayments(@Valid @RequestBody PaymentRequest[] request) {
        return accountService.registerPayments(request);
    }

    @PutMapping("/payments")
    public ResponseEntity<PaymentsSavedResponse> updatePayments(@Valid @RequestBody PaymentRequest request) {
        return accountService.updatePayments(request);
    }
}