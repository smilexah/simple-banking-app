package sdu.edu.kz.banking_app.dto;

public record TransferFundDTO(
        Long fromAccountID,
        Long toAccountID,
        Double amount
) {
}
