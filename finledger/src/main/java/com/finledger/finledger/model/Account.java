package com.finledger.finledger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Version
    private Long version;
}