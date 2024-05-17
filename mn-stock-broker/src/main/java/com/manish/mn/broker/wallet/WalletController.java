package com.manish.mn.broker.wallet;

import com.manish.mn.api.IRestApiResponse;
import com.manish.mn.broker.data.InMemoryAccountStore;
import com.manish.mn.broker.error.CustomError;
import com.manish.mn.broker.error.FiatCurrencyNotSupportedException;
import com.manish.mn.broker.watchlist.WatchlistController;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller("/account/wallets")
public record WalletController(InMemoryAccountStore store) {
    public static final List<String> SUPPORTED_FIAT_CURRENCIES = List.of("EUR", "USD", "CHF", "GBP");
//    public static final UUID ACCOUNT_ID = UUID.randomUUID();
    public static final UUID ACCOUNT_ID = InMemoryAccountStore.ACCOUNT_ID;
    private static final Logger LOG = LoggerFactory.getLogger(WatchlistController.class);

    @Get(produces = MediaType.APPLICATION_JSON)
    public Collection<Wallet> get() { return store.getWallets(ACCOUNT_ID); }

    /*IRestApiResponse acts as a Marker interface wherein with the help of it the method can return CustomError or Wallet object */
    @Post(
            value="/deposit",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON
    )
    public MutableHttpResponse<IRestApiResponse> depositFiatMoney(@Body DepositFiatMoney deposit) {
        if(!SUPPORTED_FIAT_CURRENCIES.contains(deposit.symbol().value())) {
            return HttpResponse.badRequest()
                    .body(new CustomError(
                            HttpStatus.BAD_REQUEST.getCode(),
                            "UNSUPPORTED FIAT CURRENCY",
                            String.format("Only %s are supported", SUPPORTED_FIAT_CURRENCIES)
                    ));
        }

        var wallet = store.depositToWallet(deposit);
        LOG.debug("Deposit to wallet {}", wallet);

        return HttpResponse.ok().body(wallet);
    }

    @Post(
            value="/withdraw",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON
    )
    public MutableHttpResponse<Wallet> withdrawFiatMoney(@Body DepositFiatMoney withdraw) {
        if (!SUPPORTED_FIAT_CURRENCIES.contains(withdraw.symbol().value())) {
            throw new FiatCurrencyNotSupportedException(String.format("Only %s are supported", SUPPORTED_FIAT_CURRENCIES));
        }

        var wallet = store.withdrawFromWallet(withdraw);
        LOG.debug("Withdraw from wallet {}", wallet);

        return HttpResponse.ok().body(wallet);
    }

}
