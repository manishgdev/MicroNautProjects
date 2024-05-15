package com.manish.mn.broker;

import com.manish.mn.broker.data.InMemoryStore;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.ArrayList;
import java.util.List;

@Controller ("/symbols")
public class SymbolsController {

    private final InMemoryStore inMemoryStore;

    public SymbolsController(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }

    @Get
    public List<Symbol> symbols() {
        return new ArrayList<>(inMemoryStore.getSymbols().values());
    }
}
