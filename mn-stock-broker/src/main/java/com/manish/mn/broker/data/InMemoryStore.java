package com.manish.mn.broker.data;

import com.github.javafaker.Faker;
import com.manish.mn.broker.Symbol;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.stream.IntStream;

@Singleton
public class InMemoryStore {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryStore.class);
    private final HashMap<String, Symbol> symbols = new HashMap<>();
    private final Faker faker = new Faker();

    @PostConstruct
    public void initialize() {
        IntStream.range(0, 10).forEach(i ->
            addNewSymbol()
        );
    }

    private void addNewSymbol() {
        var symbol = new Symbol(faker.stock().nsdqSymbol());
        symbols.put(symbol.value(), symbol);
        log.debug("Added symbols");
    }

    public HashMap<String, Symbol> getSymbols() {
        return symbols;
    }
}
