package sdu.edu.kz.banking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long accountId;

    @Column(name = "transaction_type")
    private String transactionType; // DEPOSIT, WITHDRAW, TRANSFER

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "transaction_time")
    private LocalDateTime timestamp;
}
