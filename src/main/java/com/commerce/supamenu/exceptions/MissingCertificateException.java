package com.commerce.supamenu.exceptions;

public class MissingCertificateException extends RuntimeException {
    public MissingCertificateException(String message) {
        super(message);
    }
}