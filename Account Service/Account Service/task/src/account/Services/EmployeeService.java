package account.Services;

import account.Entities.Payment;
import account.Entities.User;
import account.Repositories.PaymentRepository;
import account.Responses.PaymentHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public EmployeeService(UserService userService, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    public ResponseEntity<?> getPayments(String period) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User employee = userService.getUserByEmail(email);

        if (period == null) {
            if (paymentRepository.findAllByEmployeeEmailOrderByDateDesc(employee.getEmail()).isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<PaymentHistoryResponse> payments = new ArrayList<>();

            for (Payment payment : paymentRepository.findAllByEmployeeEmailOrderByDateDesc(employee.getEmail())) {
                payments.add(new PaymentHistoryResponse(payment));
            }

            return ResponseEntity.ok(payments);
        }

        if (!paymentRepository.existsByEmployeeEmailAndPeriod(employee.getEmail(), period)) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        Payment payment = paymentRepository.findByEmployeeEmailAndPeriod(employee.getEmail(), period);

        return ResponseEntity.ok(new PaymentHistoryResponse(payment));
    }
}