package com.thefisola.customerservice.exception;

public class InvalidLoginException extends BaseException {
    public InvalidLoginException() {
        super("Invalid credentials");
    }
}
