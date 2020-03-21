package ml.socshared.adapter.fb.controller.v1;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.api.v1.rest.FacebookAuthorizationApi;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.exception.impl.HttpUnavailableRequestException;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FacebookAuthorizationController implements FacebookAuthorizationApi {

    private FacebookAuthorizationService authService;
    private UUID systemUserId;

    public FacebookAuthorizationController(FacebookAuthorizationService authService) {
        this.authService = authService;
    }

    @Override
    @GetMapping(value = "/access/{systemUserId}")
    public void getAccess(@PathVariable UUID systemUserId, HttpServletResponse response) throws Exception {
        this.systemUserId = systemUserId;
        String url = authService.getURLForAccess();
        response.sendRedirect(url);
    }

    @Override
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getToken(@RequestParam("code") String authorizationCode) {
        if (systemUserId == null)
            throw new HttpUnavailableRequestException();
        return authService.getToken(systemUserId, authorizationCode);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getUserDataBySystemUserId(@PathVariable UUID systemUserId) {
        return authService.findUserDataBySystemUserId(systemUserId);
    }

}
