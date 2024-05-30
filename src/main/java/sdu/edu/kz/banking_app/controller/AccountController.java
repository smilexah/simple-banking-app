package sdu.edu.kz.banking_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.edu.kz.banking_app.dto.AccountDTO;
import sdu.edu.kz.banking_app.dto.TransactionDTO;
import sdu.edu.kz.banking_app.dto.TransferFundDTO;
import sdu.edu.kz.banking_app.service.AccountService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountByID(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountService.getAccountByID(accountId), HttpStatus.OK);
    }

    @PutMapping("/{accountId}/add-balance")
    public ResponseEntity<AccountDTO> addBalanceByAccountID(@PathVariable Long accountId, @RequestBody Map<String, Double> request) {
        AccountDTO accountDTO = accountService.addBalanceAccountByID(accountId, request.get("amount"));
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{accountId}/withdraw-balance")
    public ResponseEntity<AccountDTO> withdrawDepositAccountByID(@PathVariable Long accountId, @RequestBody Map<String, Double> request) {
        AccountDTO accountDTO = accountService.withdrawDepositAccountByID(accountId, request.get("amount"));
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccountByID(@PathVariable Long accountId) {
        accountService.deleteAccountByID(accountId);
        return ResponseEntity.ok("Account deleted successfully!");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransferFundDTO transferFund) {
        accountService.transferFunds(transferFund);
        return ResponseEntity.ok("Funds transferred successfully!");
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getAccountTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountTransactions(accountId));
    }
}
