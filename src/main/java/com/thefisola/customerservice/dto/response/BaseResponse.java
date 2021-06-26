package com.thefisola.customerservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class BaseResponse<T> extends ResponseEntity<T> {
    private HttpStatus status;
    private T data;
    private String message = "Successful";

    public BaseResponse(HttpStatus status) {
        super(status);
        this.status = status;
    }

    public BaseResponse(HttpStatus status, String message) {
        super(status);
        this.status = status;
        this.message = message;
    }

    public BaseResponse(T body, HttpStatus status) {
        super(body, status);
        this.data = body;
        this.status = status;
    }

    public BaseResponse(T body, HttpStatus status, String message) {
        super(body, status);
        this.data = body;
        this.status = status;
        this.message = message;
    }
}
