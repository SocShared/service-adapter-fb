package ml.socshared.adapter.fb.exception.impl;

import org.springframework.http.HttpStatus;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.SocsharedErrors;

public class HttpNotFoundException extends AbstractRestHandleableException {
    public HttpNotFoundException() {
        super(SocsharedErrors.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public HttpNotFoundException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }

    public HttpNotFoundException(String message) {
        super(message, SocsharedErrors.NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}

