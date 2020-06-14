package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookStatisticsApi;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("isAuthenticated()")
public class FacebookStatisticsController implements FacebookStatisticsApi {
}
