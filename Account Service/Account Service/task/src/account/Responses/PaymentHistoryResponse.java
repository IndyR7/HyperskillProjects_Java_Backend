package account.Responses;

import account.Entities.Payment;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class PaymentHistoryResponse {
    private String name;
    private String lastname;
    private String period;
    private String salary;

    public PaymentHistoryResponse(Payment payment) {
        this.name = payment.getEmployee().getName();
        this.lastname = payment.getEmployee().getLastname();
        this.period = payment.getDate().format(DateTimeFormatter.ofPattern("MMMM-yyyy", Locale.ENGLISH));
        this.salary = String.format("%d dollar(s) %d cent(s)", payment.getSalary() / 100, payment.getSalary() % 100);
    }
}