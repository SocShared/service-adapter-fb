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
    private UUID userId;

    public FacebookAuthorizationController(FacebookAuthorizationService authService) {
        this.authService = authService;
    }

    @Override
    @GetMapping(value = "/access/{userId}")
    public void getAccess(@PathVariable UUID userId, HttpServletResponse response) throws Exception {
        this.userId = userId;
        String url = authService.getURLForAccess();
        response.sendRedirect(url);
    }

    @Override
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getToken(@RequestParam("code") String authorizationCode) {
        if (userId == null)
            throw new HttpUnavailableRequestException();
        return authService.getToken(userId, authorizationCode);
    }

    @Override
    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getUserDataByUserId(@PathVariable UUID userId) {
        return authService.findUserDataById(userId);
    }

}
