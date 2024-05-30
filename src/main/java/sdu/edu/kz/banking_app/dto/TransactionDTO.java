package sdu.edu.kz.banking_app.dto;

import java.time.LocalDateTime;

public record TransactionDTO(
        Long transactionId,
        Long accountId,
        Double amount,
        String transactionType,
        LocalDateTime timestamp
) {
}
