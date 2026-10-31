package com.maxi.transaction_service.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.Objects;


public final class Transaction {
    private final UUID id;
    private final String sourceAccount;
    private final String destinationAccount;
    private final BigDecimal amount;
    private final CurrencyCode currency;
    private final TransactionStatus status;
    private final Instant createdAt;

    private Transaction(UUID id, String sourceAccount, String destinationAccount, BigDecimal amount, CurrencyCode currency, TransactionStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.sourceAccount = Objects.requireNonNull(sourceAccount);
        this.destinationAccount = Objects.requireNonNull(destinationAccount);
        this.amount = Objects.requireNonNull(amount);
        this.currency = Objects.requireNonNull(currency);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static Transaction createPending(String sourceAccount, String destinationAccount, BigDecimal amount, CurrencyCode currency) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (sourceAccount.equals(destinationAccount)) {
            throw new IllegalArgumentException("Origen y destino no pueden ser la misma cuenta");
        }
        return new Transaction(UUID.randomUUID(), sourceAccount, destinationAccount, amount, currency, TransactionStatus.PENDING, Instant.now());
    }

    public Transaction complete(){
        if(status!=TransactionStatus.PENDING){
            throw new IllegalStateException("Solo se puede completar una transacción pendiente");
        }
        return new Transaction(id,sourceAccount,destinationAccount,amount,currency,TransactionStatus.COMPLETED,createdAt);
    }

    public UUID id() {
        return id;
    }

    public String sourceAccount() {
        return sourceAccount;
    }

    public String destinationAccount() {
        return destinationAccount;
    }

    public BigDecimal amount() {
        return amount;
    }

    public CurrencyCode currency() {
        return currency;
    }

    public TransactionStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }
}


