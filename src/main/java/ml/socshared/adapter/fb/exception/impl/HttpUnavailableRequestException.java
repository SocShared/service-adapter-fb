package ml.socshared.adapter.fb.exception.impl;

import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.SocsharedErrors;
import org.springframework.http.HttpStatus;

public class HttpUnavailableRequestException extends AbstractRestHandleableException {
    public HttpUnavailableRequestException() {
        super(SocsharedErrors.UNAVAILABLE_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpUnavailableRequestException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
