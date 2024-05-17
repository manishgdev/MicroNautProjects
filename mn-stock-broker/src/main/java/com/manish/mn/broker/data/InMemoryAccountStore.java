package com.manish.mn.broker.data;

import com.manish.mn.broker.model.Symbol;
import com.manish.mn.broker.model.WatchList;
import com.manish.mn.broker.wallet.DepositFiatMoney;
import com.manish.mn.broker.wallet.Wallet;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.*;


@Singleton
public class InMemoryAccountStore {

    public static final UUID ACCOUNT_ID = UUID.fromString("146236e8-4896-481f-99b8-87408b9e2811");

    private final HashMap<UUID, WatchList> watchListsPerAccount = new HashMap<>();
    private final HashMap<UUID, Map<UUID, Wallet>> walletsPerAccount = new HashMap<>();

    public WatchList getWatchList(final UUID accountId) {
        return watchListsPerAccount.getOrDefault(accountId, new WatchList());
    }

    public WatchList updateWatchList(final UUID accountId, final WatchList watchList) {
        watchListsPerAccount.put(accountId, watchList);
        return getWatchList(accountId);
    }

    public void deleteWatchList(final UUID accountId) {
        watchListsPerAccount.remove(accountId);
    }

    public Collection<Wallet> getWallets(UUID accountID) {
        return Optional.ofNullable(walletsPerAccount.get(accountID))
                .orElse(new HashMap<>())
                .values();
    }

    public Wallet depositToWallet(DepositFiatMoney deposit) {
        return updateWallet(walletsPerAccount, deposit.accountId(), deposit.walletId(), deposit.symbol(), deposit.amount());
    }

    public Wallet withdrawFromWallet(DepositFiatMoney withdraw) {
        return updateWallet(walletsPerAccount, withdraw.accountId(), withdraw.walletId(), withdraw.symbol(), withdraw.amount().negate());
    }

    private Wallet updateWallet(
            HashMap<UUID, Map<UUID, Wallet>> walletsPerAccount,
            UUID accountId,
            UUID walletId,
            Symbol symbol,
            BigDecimal amount) {

        final var wallets = Optional.ofNullable(
                walletsPerAccount.get(accountId)
        ).orElse(
                new HashMap<>()
        );

        var oldWallet = Optional.ofNullable(
                wallets.get(walletId)
        ).orElse(
                new Wallet(accountId, walletId, symbol, BigDecimal.ZERO, BigDecimal.ZERO)
        );

        var newWallet = oldWallet.addToWallet(amount);

        wallets.put(newWallet.walletId(), newWallet);
        walletsPerAccount.put(newWallet.accountId(), wallets);

        return newWallet;
    }
}