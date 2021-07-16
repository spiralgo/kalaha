package com.bol.kalaha.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class KalahaException extends Exception {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public KalahaException(HttpStatus httpStatus, String message) {
        super(message);
        if (httpStatus != null) {
            this.httpStatus = httpStatus;
        }
        log.debug(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}