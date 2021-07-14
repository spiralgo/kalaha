package com.bol.kalaha.exception;

import org.springframework.http.HttpStatus;

public class KalahaException extends Exception {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public KalahaException(HttpStatus httpStatus, String message) {
        super(message);
        if(httpStatus!=null) {
            this.httpStatus = httpStatus;
        }
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}