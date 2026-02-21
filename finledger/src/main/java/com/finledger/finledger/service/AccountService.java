package com.finledger.finledger.service;


import com.finledger.finledger.dto.AccountRequest;
import com.finledger.finledger.model.Account;
import com.finledger.finledger.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(AccountRequest request) {

        Account account = new Account();
        account.setCustomerName(request.getCustomerName());
        account.setBalance(
                request.getInitialBalance() != null
                        ? request.getInitialBalance()
                        : BigDecimal.ZERO
        );

        return accountRepository.save(account);
    }

    public Account getAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
