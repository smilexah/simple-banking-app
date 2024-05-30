package sdu.edu.kz.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.edu.kz.banking_app.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);
}
