package ml.socshared.adapter.fb.security.client;

import ml.socshared.adapter.fb.security.request.CheckTokenRequest;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-client", url = "${feign.url.auth:}")
public interface AuthClient {

    @PostMapping(value = "/api/v1/public/service/validate_token", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponse send(CheckTokenRequest request);

}
