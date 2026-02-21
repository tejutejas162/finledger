package com.finledger.finledger.service;

import com.finledger.finledger.dto.TransferRequest;
import com.finledger.finledger.model.Account;
import com.finledger.finledger.model.IdempotencyKey;
import com.finledger.finledger.model.LedgerEntry;
import com.finledger.finledger.repository.AccountRepository;
import com.finledger.finledger.repository.IdempotencyRepository;
import com.finledger.finledger.repository.LedgerEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerRepository;
    private final KafkaProducerService kafkaProducerService;
    private final IdempotencyRepository idempotencyRepository;


    public void transfer(UUID fromId, UUID toId, BigDecimal amount) {

        Account sender = accountRepository.findByIdForUpdate(fromId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findByIdForUpdate(toId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        UUID transactionId = UUID.randomUUID();

        ledgerRepository.save(new LedgerEntry(transactionId, fromId, amount, BigDecimal.ZERO));
        ledgerRepository.save(new LedgerEntry(transactionId, toId, BigDecimal.ZERO, amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        /*kafkaProducerService.publishTransactionEvent(
                "Transaction " + transactionId + " completed successfully"
        );*/
    }

    @Transactional
    public void processTransfer(String idempotencyKey, TransferRequest request) {

        if (idempotencyRepository.findByIdempotencyKey(idempotencyKey).isPresent()) {
            throw new RuntimeException("Duplicate request detected");
        }

        transfer(request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount());

        IdempotencyKey key = new IdempotencyKey();
        key.setIdempotencyKey(idempotencyKey);
        idempotencyRepository.save(key);
    }
}
