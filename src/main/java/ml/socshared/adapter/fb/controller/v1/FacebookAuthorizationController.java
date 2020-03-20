package ml.socshared.adapter.fb.controller.v1;

import io.swagger.annotations.ResponseHeader;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class FacebookAuthorizationController {

    private FacebookAuthorizationService authService;

    public FacebookAuthorizationController(FacebookAuthorizationService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/access/{userId}")
    public void getAccess(@PathVariable UUID userId, HttpServletResponse response) throws Exception {
        String url = authService.getAccess(userId);
        response.sendRedirect(url);
    }

    @GetMapping(value = "/token/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private AccessGrant getToken(@PathVariable UUID userId, @RequestParam("code") String authorizationCode) {
        return authService.getToken(userId, authorizationCode);
    }

}
