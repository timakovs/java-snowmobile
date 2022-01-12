package org.example.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException() {
    }

    public RentNotFoundException(String message) {
        super(message);
    }

    public RentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentNotFoundException(Throwable cause) {
        super(cause);
    }

    public RentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
