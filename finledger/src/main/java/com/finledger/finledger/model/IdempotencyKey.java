package com.finledger.finledger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
public class IdempotencyKey {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(unique = true)
    private String idempotencyKey;

    private LocalDateTime createdAt = LocalDateTime.now();
}