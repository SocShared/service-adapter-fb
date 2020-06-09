package ml.socshared.adapter.fb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.exception.AbstractRestHandleableException;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.SocialException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<RestApiError> buildErrorResponse(Exception exc, HttpStatus httpStatus,
                                                            ServletWebRequest webRequest) {
        return new ResponseEntity<>(new RestApiError(exc, httpStatus, webRequest), httpStatus);
    }

    @ExceptionHandler(AbstractRestHandleableException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, AbstractRestHandleableException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, exc.getHttpStatus(), webRequest);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, HttpClientErrorException exc) throws Exception {
        log.error(exc.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = mapper.readValue(exc.getMessage(), HashMap.class);
        Map<String, Object> object = (HashMap) json.get("error");
        String message = (String) object.get("message");
        return buildErrorResponse(new HttpClientErrorException(exc.getStatusCode(), message), exc.getStatusCode(), webRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, ServletException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, HttpStatus.NOT_FOUND, webRequest);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, RateLimitExceededException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, HttpStatus.BAD_GATEWAY, webRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SocialException.class)
    public ResponseEntity<RestApiError> handlePrintException(ServletWebRequest webRequest, SocialException exc) {
        log.error(exc.getMessage());
        return buildErrorResponse(exc, HttpStatus.BAD_REQUEST, webRequest);
    }

}

