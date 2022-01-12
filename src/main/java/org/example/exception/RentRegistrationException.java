package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RentRegistrationException extends RuntimeException {
    public RentRegistrationException() {
    }

    public RentRegistrationException(String message) {
        super(message);
    }

    public RentRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentRegistrationException(Throwable cause) {
        super(cause);
    }

    public RentRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

