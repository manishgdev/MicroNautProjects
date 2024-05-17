package com.manish.mn.broker.wallet;

import com.manish.mn.api.IRestApiResponse;
import com.manish.mn.broker.model.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

public record Wallet(
        UUID accountId,
        UUID walletId,
        Symbol symbol,
        BigDecimal availableValue,
        BigDecimal locked
) implements IRestApiResponse {

    public Wallet depositToWallet(BigDecimal amount) {
        return new Wallet(
                this.accountId,
                this.walletId,
                this.symbol,
                this.availableValue.add(amount),
                this.locked
        );
    }
}
