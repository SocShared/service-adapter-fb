package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookStatisticsApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookStatisticsController implements FacebookStatisticsApi {
}