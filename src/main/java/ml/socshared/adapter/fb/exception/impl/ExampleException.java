package ml.socshared.adapter.fb.exception.impl;

import org.springframework.http.HttpStatus;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.AswErrors;

public class ExampleException extends AbstractRestHandleableException {
    public ExampleException() {
        super(AswErrors.EXAMPLE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ExampleException(AswErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}

