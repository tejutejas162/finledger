package com.finledger.finledger.controller;

import com.finledger.finledger.dto.TransferRequest;
import com.finledger.finledger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;



    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody TransferRequest request) {

        service.processTransfer(idempotencyKey, request);
        return ResponseEntity.ok("Transfer successful");
    }
}
