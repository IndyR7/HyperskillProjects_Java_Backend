package antifraud.Services;

import antifraud.Constraints.FormattingConstraints;
import antifraud.Entities.Transaction;
import antifraud.Repositories.StolenCardRepository;
import antifraud.Repositories.SuspiciousIPRepository;
import antifraud.Repositories.TransactionRepository;
import antifraud.Requests.TransactionRequest;
import antifraud.Responses.TransactionExecutedResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class TransactionValidationService {
    private TransactionRequest request;
    private String result;
    private List<String> errors;
    private TransactionRepository transactionRepository;
    private SuspiciousIPRepository suspiciousIpRepository;
    private StolenCardRepository stolenCardRepository;

    @Autowired
    public TransactionValidationService(TransactionRepository transactionRepository,
                                        SuspiciousIPRepository suspiciousIpRepository,
                                        StolenCardRepository stolenCardRepository) {
        this.transactionRepository = transactionRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.stolenCardRepository = stolenCardRepository;
    }

    public void validateTransaction(TransactionRequest request) {
        this.request = request;
        this.errors = new ArrayList<>();
        this.result = "ALLOWED";

        if (isIllegalTransaction()) {
            return;
        }

        for (String field : getAllResults().keySet()) {
            if (getAllResults().get(field).equals("MANUAL_PROCESSING") && this.result.equals("PROHIBITED")
                    || getAllResults().get(field).equals("ALLOWED")) {
                continue;
            }

            if (getAllResults().get(field).equals("PROHIBITED") && !this.result.equals("PROHIBITED")) {
                this.errors = new ArrayList<>();
            }

            this.result = getAllResults().get(field);

            this.errors.add(field);
        }
    }

    public ResponseEntity<TransactionExecutedResponse> getValidatedTransaction() {
        if (isIllegalTransaction()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        transactionRepository.save(new Transaction(request.getIP(), request.getNumber(), request.getAmount(),
                request.getRegion(),
                LocalDateTime.parse(request.getDate())));

        return ResponseEntity.ok(new TransactionExecutedResponse(result, getInfo()));
    }

    private boolean isIllegalTransaction() {
        return !FormattingConstraints.isValidIp(request.getIP())
                || !FormattingConstraints.isValidCardNumber(request.getNumber())
                || !FormattingConstraints.isValidAmount(request.getAmount())
                || !FormattingConstraints.isValidDate(request.getDate())
                || !FormattingConstraints.isValidRegion(request.getRegion());
    }

    private Map<String, String> getAllResults() {
        Map<String, String> allResults = new HashMap<>();

        allResults.put("amount", getResultByAmount());
        allResults.put("card-number", getResultByCardNumber());
        allResults.put("ip", getResultBySuspiciousIp());
        allResults.put("ip-correlation", getResultByIp());
        allResults.put("region-correlation", getResultByRegion());

        return allResults;
    }

    private String getResultBySuspiciousIp() {
        return !suspiciousIpRepository.existsByIp(request.getIP()) ? "ALLOWED"
                : "PROHIBITED";
    }

    private String getResultByCardNumber() {
        return !stolenCardRepository.existsByNumber(request.getNumber()) ? "ALLOWED"
                : "PROHIBITED";
    }

    private String getResultByAmount() {
        return request.getAmount() <= 200 ? "ALLOWED"
                : request.getAmount() <= 1500 ? "MANUAL_PROCESSING"
                : "PROHIBITED";
    }

    private String getResultByRegion() {
        List<String> regions = new ArrayList<>();
        int count = 0;

        for (Transaction transaction : getTransactionsInLastHours()) {
            if (!transaction.getRegion().equals(request.getRegion()) && !regions.contains(transaction.getRegion())) {
                regions.add(transaction.getRegion());
                count++;
            }
        }

        return count < 2 ? "ALLOWED" : count == 2 ? "MANUAL_PROCESSING" : "PROHIBITED";
    }

    private String getResultByIp() {
        List<String> ips = new ArrayList<>();
        int count = 0;

        for (Transaction transaction : getTransactionsInLastHours()) {
            if (!transaction.getIp().equals(request.getIP()) && !ips.contains(transaction.getIp())) {
                ips.add(transaction.getIp());
                count++;
            }
        }

        return count < 2 ? "ALLOWED" : count == 2 ? "MANUAL_PROCESSING" : "PROHIBITED";
    }

    private List<Transaction> getTransactionsInLastHours() {
        LocalDateTime requestDateTime = LocalDateTime.parse(request.getDate());
        LocalDateTime requestDateTimeMinusOneHour = requestDateTime.minusHours(1);

        return transactionRepository
                .findByNumberAndDateBetween(request.getNumber(), requestDateTimeMinusOneHour, requestDateTime);
    }

    private String getInfo() {
        return this.errors.isEmpty() ? "none"
                : errors
                .stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}