package sdu.edu.kz.banking_app.mapper;

import sdu.edu.kz.banking_app.dto.AccountDTO;
import sdu.edu.kz.banking_app.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDTO accountDTO) {
        return new Account(
//                accountDTO.getId(),
//                accountDTO.getAccountHolderName(),
//                accountDTO.getBalance()
                accountDTO.accountId(),
                accountDTO.accountHolderName(),
                accountDTO.balance()
        );
    }

    public static AccountDTO mapToAccountDTO(Account account) {
        return new AccountDTO(
                account.getAccountId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }
}
