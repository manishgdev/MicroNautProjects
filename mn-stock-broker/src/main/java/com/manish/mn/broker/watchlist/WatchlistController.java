package com.manish.mn.broker.watchlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.mn.broker.data.InMemoryAccountStore;
import com.manish.mn.broker.model.WatchList;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Controller("/account/watchlist")
public record WatchlistController(InMemoryAccountStore store) {

    private static final Logger LOG = LoggerFactory.getLogger(WatchlistController.class);

    public static final UUID ACCOUNT_ID = UUID.randomUUID();

    @Get(produces = MediaType.APPLICATION_JSON)
    public WatchList get() {
        LOG.debug("get - {}", Thread.currentThread().getName());
        return store.getWatchList(ACCOUNT_ID);
    }

    @Put(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    public WatchList update(@Body WatchList watchList) {
        try {
            ObjectMapper om = new ObjectMapper();
            System.out.println(om.writeValueAsString(watchList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store.updateWatchList(ACCOUNT_ID, watchList);
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete(produces = MediaType.APPLICATION_JSON)
    public void delete() {
        store.deleteWatchList(ACCOUNT_ID);
    }
}
