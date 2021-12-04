package com.example.demo.exception;


public class BadRequestException extends Exception {
    private static final long serialVersionUID = -7262662137291242514L;
    private String code;
    private String message;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}