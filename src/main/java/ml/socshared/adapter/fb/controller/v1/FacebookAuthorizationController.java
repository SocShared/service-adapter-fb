package ml.socshared.adapter.fb.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.api.v1.rest.FacebookAuthorizationApi;
import ml.socshared.adapter.fb.domain.response.AccessUrlResponse;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FacebookAuthorizationController implements FacebookAuthorizationApi {

    private final FacebookAuthorizationService authService;

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/access")
    public AccessUrlResponse getAccessUrl() {

        String url = authService.getURLForAccess();

        return AccessUrlResponse.builder().urlForAccess(url).build();
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/code/{authorizationCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getTokenFacebook(@PathVariable UUID systemUserId, @PathVariable String authorizationCode) {

        return authService.getToken(systemUserId, authorizationCode);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/facebook/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getUserDataBySystemUserId(@PathVariable UUID systemUserId) {

        return authService.findUserDataBySystemUserId(systemUserId);
    }

}
