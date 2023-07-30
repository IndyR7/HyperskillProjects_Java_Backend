package account.Services;

import account.Entities.Payment;
import account.Entities.User;
import account.Exceptions.UserNotFoundException;
import account.Exceptions.UserPeriodComboExistsException;
import account.Exceptions.UserPeriodComboNotFoundException;
import account.Repositories.PaymentRepository;
import account.Repositories.UserRepository;
import account.Requests.PaymentRequest;
import account.Responses.PaymentsSavedResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public AccountService(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public ResponseEntity<PaymentsSavedResponse> registerPayments(PaymentRequest[] request) {
        List<String> paymentsProcessed = new ArrayList<>();

        for (PaymentRequest paymentRequest : request) {
            if (!userRepository.existsByEmailIgnoreCase(paymentRequest.getEmployee())) {
                throw new UserNotFoundException();
            }

            if (paymentRepository.existsByEmployeeEmailAndPeriod(
                    paymentRequest.getEmployee(), paymentRequest.getPeriod())
                    || paymentsProcessed.contains(paymentRequest.getEmployee() + paymentRequest.getPeriod())) {
                throw new UserPeriodComboExistsException();
            }

            User employee = userRepository.findByEmailIgnoreCase(paymentRequest.getEmployee());
            Payment payment = new Payment();

            payment.setValues(employee, paymentRequest.getPeriod(), paymentRequest.getSalary());

            paymentRepository.save(payment);
            paymentsProcessed.add(paymentRequest.getEmployee() + paymentRequest.getPeriod());
        }

        return ResponseEntity.ok(new PaymentsSavedResponse("Added successfully!"));
    }

    @Transactional
    public ResponseEntity<PaymentsSavedResponse> updatePayments(PaymentRequest request) {
        if (!userRepository.existsByEmailIgnoreCase(request.getEmployee())) {
            throw new UserNotFoundException();
        }

        if (!paymentRepository.existsByEmployeeEmailAndPeriod(request.getEmployee(), request.getPeriod())) {
            throw new UserPeriodComboNotFoundException();
        }

        Payment payment = paymentRepository.findByEmployeeEmailAndPeriod(request.getEmployee(), request.getPeriod());

        payment.setSalary(request.getSalary());

        paymentRepository.save(payment);

        return ResponseEntity.ok(new PaymentsSavedResponse("Updated successfully!"));
    }
}