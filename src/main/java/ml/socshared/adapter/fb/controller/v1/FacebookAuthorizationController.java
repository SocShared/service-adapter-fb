package ml.socshared.adapter.fb.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.api.v1.rest.FacebookAuthorizationApi;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class FacebookAuthorizationController implements FacebookAuthorizationApi {

    private final FacebookAuthorizationService authService;

    @Override
    @GetMapping(value = "/access")
    public Map<String, String> getAccessUrl() {

        String url = authService.getURLForAccess();

        return new HashMap<>() {
            { put("url_for_access", url); }
        };
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/code/{authorizationCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessGrant getTokenFacebook(@PathVariable UUID systemUserId, @PathVariable String authorizationCode) {

        return authService.getToken(systemUserId, authorizationCode);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/facebook/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getUserDataBySystemUserId(@PathVariable UUID systemUserId) {

        return authService.findUserDataBySystemUserId(systemUserId);
    }

}
