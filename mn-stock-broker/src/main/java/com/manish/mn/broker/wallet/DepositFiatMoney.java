package com.manish.mn.broker.wallet;

import com.manish.mn.broker.model.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositFiatMoney (
        UUID accountId,
        UUID walletId,
        Symbol symbol,
        BigDecimal amount
) {
}
