package antifraud.Repositories;


import antifraud.Entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findByIp(String ip);

    List<Transaction> findByNumberAndDateBetween(String number, LocalDateTime startTime, LocalDateTime endTime);

    boolean existsByIp(String ip);

    void deleteByIp(String ip);

}