package com.manish.mn.broker.error;

public class FiatCurrencyNotSupportedException extends RuntimeException {
    public FiatCurrencyNotSupportedException(String message) {
        super(message);
    }
}
