package sdu.edu.kz.banking_app.dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class AccountDTO {
//    private Long id;
//    private String accountHolderName;
//    private Double balance;
//}

public record AccountDTO(
        Long accountId,
        String accountHolderName,
        Double balance
) {
}