package ml.socshared.adapter.fb.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.api.v1.rest.FacebookAuthorizationApi;
import ml.socshared.adapter.fb.domain.response.AccessUrlResponse;
import ml.socshared.adapter.fb.domain.response.AccountCountResponse;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(value = "/private/users/{systemUserId}/code/{authorizationCode}")
    public SuccessResponse saveAccountFacebook(@PathVariable UUID systemUserId, @PathVariable String authorizationCode) {
        return authService.saveAccountFacebook(systemUserId, authorizationCode);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/facebook/data")
    public FacebookUserResponse getUserDataBySystemUserId(@PathVariable UUID systemUserId) {
        return authService.findUserDataBySystemUserId(systemUserId);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @DeleteMapping(value = "/private/users/{systemUserId}/facebook")
    public void deleteAccountFacebook(@PathVariable UUID systemUserId) {
        authService.deleteFacebookAccount(systemUserId);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/account/count")
    public AccountCountResponse accountCount() {
        return authService.count();
    }
}
