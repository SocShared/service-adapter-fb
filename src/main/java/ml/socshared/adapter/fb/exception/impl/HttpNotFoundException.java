package ml.socshared.adapter.fb.exception.impl;

import org.springframework.http.HttpStatus;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;

public class HttpNotFoundException extends AbstractRestHandleableException {
    public HttpNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public HttpNotFoundException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public HttpNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

