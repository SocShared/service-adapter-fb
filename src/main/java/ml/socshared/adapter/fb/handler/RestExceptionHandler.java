package ml.socshared.adapter.fb.handler;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.SocsharedErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<RestApiError> buildErrorResponse(Exception exc, HttpStatus httpStatus,
                                                            ServletWebRequest webRequest, SocsharedErrors errorCode) {
        return new ResponseEntity<>(new RestApiError(exc, httpStatus, webRequest, errorCode), httpStatus);
    }

    @ExceptionHandler(AbstractRestHandleableException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, AbstractRestHandleableException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, exc.getHttpStatus(), webRequest, exc.getErrorCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, ServletException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, HttpStatus.NOT_FOUND, webRequest, SocsharedErrors.NOT_FOUND);
    }
}

