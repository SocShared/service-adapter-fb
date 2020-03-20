package ml.socshared.adapter.fb.exception.impl;

import org.springframework.http.HttpStatus;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.AswErrors;

public class HttpNotFoundException extends AbstractRestHandleableException {
    public HttpNotFoundException() {
        super(AswErrors.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public HttpNotFoundException(AswErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}

