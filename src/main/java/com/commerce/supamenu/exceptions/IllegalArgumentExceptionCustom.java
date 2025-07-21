package com.commerce.supamenu.exceptions;

public class IllegalArgumentExceptionCustom extends RuntimeException {
    public IllegalArgumentExceptionCustom(String message) {
        super(message);
    }
}