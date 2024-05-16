package com.manish.mn.broker.model;

import java.util.ArrayList;
import java.util.List;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record WatchList(List<Symbol> symbols) {
    public WatchList() { this(new ArrayList<Symbol>()); }
}
