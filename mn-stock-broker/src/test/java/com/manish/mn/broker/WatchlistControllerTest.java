package com.manish.mn.broker;

import com.fasterxml.jackson.databind.JsonNode;
import com.manish.mn.broker.data.InMemoryAccountStore;
import com.manish.mn.broker.model.Symbol;
import com.manish.mn.broker.model.WatchList;
import com.manish.mn.broker.watchlist.WatchlistController;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@MicronautTest
public class WatchlistControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(WatchlistControllerTest.class);
    private static final UUID TEST_ACCOUNT_ID = WatchlistController.ACCOUNT_ID;

    @Inject
    InMemoryAccountStore inMemoryAccountStore;

    @Inject
    @Client("/account/watchlist")
    HttpClient client;

    @BeforeEach
    void setUp() {
        inMemoryAccountStore.deleteWatchList(TEST_ACCOUNT_ID);
    }

    @Test
    void test1() {
        final WatchList result = client.toBlocking().retrieve("/", WatchList.class);
        assertNull(result.symbols());
    }

    @Test
    void test2() {
        inMemoryAccountStore.updateWatchList(TEST_ACCOUNT_ID, new WatchList(
                Stream.of("APPL", "GOOGL", "MSFT")
                        .map(Symbol::new)
                        .toList()
        ));

        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals("""
//                {
//                  "symbols" : [ {
//                    "value" : "APPL"
//                  }, {
//                    "value" : "GOOGL"
//                  }, {
//                    "value" : "MSFT"
//                  } ]
//                }
//                """, response.getBody().get().toPrettyString());
    }

    @Test
    void test3() {
        var symbols = Stream.of("APPL", "GOOGL", "MSFT").map(Symbol::new).toList();
        final var request = HttpRequest.PUT("/", new WatchList(symbols))
                .accept(MediaType.APPLICATION_JSON);
        HttpResponse<Object> response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(symbols, inMemoryAccountStore.getWatchList(TEST_ACCOUNT_ID).symbols());

    }
}
