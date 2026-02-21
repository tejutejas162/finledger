package com.finledger.finledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ledger_entries")
@Getter
@Setter
@NoArgsConstructor
public class LedgerEntry {


    @Id
    private UUID id = UUID.randomUUID();

    private UUID transactionId;

    private UUID accountId;

    private BigDecimal debit;

    private BigDecimal credit;

    public LedgerEntry(UUID transactionId, UUID accountId,
                       BigDecimal debit, BigDecimal credit) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.debit = debit;
        this.credit = credit;
    }

}
