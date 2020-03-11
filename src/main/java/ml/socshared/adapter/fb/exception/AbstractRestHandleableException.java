package ml.socshared.adapter.fb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractRestHandleableException extends RuntimeException implements HttpStatusCodeContainer {
    private static final long serialVersionUID = -3416823984750319182L;

    private HttpStatus httpStatus;
    private AswErrors errorCode;

    public AbstractRestHandleableException(String message, AswErrors errorCode, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public AbstractRestHandleableException(AswErrors errorCode, HttpStatus httpStatus) {
        this(errorCode.getMessage(), errorCode, httpStatus);
    }

    public AbstractRestHandleableException(AswErrors errorCode, Throwable throwable, HttpStatus httpStatus) {
        super(errorCode.getMessage(), throwable);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
