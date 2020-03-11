package ru.socshared.template.exception.impl;

import org.springframework.http.HttpStatus;
import ru.socshared.template.exception.AbstractRestHandleableException;
import ru.socshared.template.exception.AswErrors;

public class ExampleException extends AbstractRestHandleableException {
    public ExampleException() {
        super(AswErrors.EXAMPLE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ExampleException(AswErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}

