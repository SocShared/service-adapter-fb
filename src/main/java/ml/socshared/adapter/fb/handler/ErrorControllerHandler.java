package ml.socshared.adapter.fb.handler;

import ml.socshared.adapter.fb.exception.SocsharedErrors;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ErrorControllerHandler implements ErrorController {

    private static final String PATH = "/error";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> error() {
        return new HashMap<>() {
            {
                put("status", 404);
                put("error", HttpStatus.NOT_FOUND);
            }
        };
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
