package com.thefisola.customerservice.exception;

public class InvalidMessageOwnerException extends BaseException {
    public InvalidMessageOwnerException() {
        super("Invalid message owner");
    }
}
