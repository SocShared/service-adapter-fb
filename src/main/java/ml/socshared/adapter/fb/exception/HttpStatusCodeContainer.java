package ml.socshared.adapter.fb.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusCodeContainer {
    HttpStatus getHttpStatus();
}

