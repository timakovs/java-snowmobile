package org.example.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.NOT_FOUND)
public class SnowmobileNotFoundException extends RuntimeException {
    public SnowmobileNotFoundException() {
    }

    public SnowmobileNotFoundException(String message) {
        super(message);
    }

    public SnowmobileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowmobileNotFoundException(Throwable cause) {
        super(cause);
    }

    public SnowmobileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
