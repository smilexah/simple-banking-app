package sdu.edu.kz.banking_app.service;

import sdu.edu.kz.banking_app.dto.AccountDTO;
import sdu.edu.kz.banking_app.dto.TransactionDTO;
import sdu.edu.kz.banking_app.dto.TransferFundDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountByID(Long accountId);
    AccountDTO addBalanceAccountByID(Long accountId, Double amount);
    AccountDTO withdrawDepositAccountByID(Long accountId, Double amount);
    List<AccountDTO> getAllAccounts();
    void deleteAccountByID(Long accountId);
    void transferFunds(TransferFundDTO transferFundDTO);
    List<TransactionDTO> getAccountTransactions(Long accountId);
}
