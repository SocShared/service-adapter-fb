package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookAccessGrantController {

    private FacebookAccessGrantService service;

    public FacebookAccessGrantController(FacebookAccessGrantService service) {
        this.service = service;
    }

    @GetMapping(value = "/access_grant", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FacebookAccessGrant> findAccessGrantAll() {
        return service.findAll();
    }

}
