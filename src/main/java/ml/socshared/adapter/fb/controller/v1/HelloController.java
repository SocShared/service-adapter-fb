package ml.socshared.adapter.fb.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import ml.socshared.adapter.fb.api.v1.rest.HelloApi;

@RestController
@RequestMapping(value = "/api/v1")
public class HelloController implements HelloApi {

    @Override
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String print() {
        return "Hello, world";
    }

}
