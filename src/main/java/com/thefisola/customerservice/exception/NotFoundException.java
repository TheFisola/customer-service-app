package com.thefisola.customerservice.exception;

public class NotFoundException extends BaseException {
    public NotFoundException() {
        super("Not found");
    }
}
