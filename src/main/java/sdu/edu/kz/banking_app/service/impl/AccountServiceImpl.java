package sdu.edu.kz.banking_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdu.edu.kz.banking_app.dto.AccountDTO;
import sdu.edu.kz.banking_app.dto.TransactionDTO;
import sdu.edu.kz.banking_app.dto.TransferFundDTO;
import sdu.edu.kz.banking_app.entity.Account;
import sdu.edu.kz.banking_app.entity.Transaction;
import sdu.edu.kz.banking_app.exception.AccountException;
import sdu.edu.kz.banking_app.exception.BalanceException;
import sdu.edu.kz.banking_app.mapper.AccountMapper;
import sdu.edu.kz.banking_app.repository.AccountRepository;
import sdu.edu.kz.banking_app.repository.TransactionRepository;
import sdu.edu.kz.banking_app.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.mapToAccount(accountDTO);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO getAccountByID(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );

        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO addBalanceAccountByID(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );

        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO withdrawDepositAccountByID(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );

        if (account.getBalance() < amount) {
            throw new BalanceException("Insufficient amount");
        }

        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts
                .stream()
                .map(AccountMapper::mapToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountByID(Long accountId) {
        accountRepository.findById(accountId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );

        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional
    public void transferFunds(TransferFundDTO transferFundDTO) {
        Account fromAccount = accountRepository
                .findById(transferFundDTO.fromAccountID())
                .orElseThrow(() -> new AccountException("Source account does not exist"));

        Account toAccount = accountRepository
                .findById(transferFundDTO.toAccountID())
                .orElseThrow(() -> new AccountException("Destination account does not exist"));

        Double amount = transferFundDTO.amount();

        if (fromAccount.getBalance() < amount) {
            throw new BalanceException("Insufficient funds in the source account");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAccountId(transferFundDTO.fromAccountID());
        transaction.setTransactionAmount(transferFundDTO.amount());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getAccountTransactions(Long accountId) {
        accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);

        return transactions
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO convertEntityToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getTransactionAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
    }
}
