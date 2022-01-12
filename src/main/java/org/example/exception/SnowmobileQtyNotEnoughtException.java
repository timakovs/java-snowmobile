package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SnowmobileQtyNotEnoughtException extends RuntimeException {
    public SnowmobileQtyNotEnoughtException() {
    }

    public SnowmobileQtyNotEnoughtException(String s) {

    }

    public SnowmobileQtyNotEnoughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowmobileQtyNotEnoughtException(Throwable cause) {
        super(cause);
    }

    public SnowmobileQtyNotEnoughtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
