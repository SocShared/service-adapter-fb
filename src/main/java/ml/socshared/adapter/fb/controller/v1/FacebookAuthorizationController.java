package ml.socshared.adapter.fb.controller.v1;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.api.v1.rest.FacebookAuthorizationApi;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.exception.impl.HttpUnavailableRequestException;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FacebookAuthorizationController implements FacebookAuthorizationApi {

    private FacebookAuthorizationService authService;

    public FacebookAuthorizationController(FacebookAuthorizationService authService) {
        this.authService = authService;
    }

    @Override
    @GetMapping(value = "/access")
    public Map<String, String> getAccessUrl(KeycloakAuthenticationToken token) {

        String url = authService.getURLForAccess();

        return new HashMap<>() {
            { put("url_for_access", url); }
        };
    }

    @Override
    @GetMapping(value = "/code_flow", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getTokenFacebook(@RequestParam("code") String authorizationCode, KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return authService.getToken(systemUserId, authorizationCode);
    }

    @Override
    @GetMapping(value = "/facebook/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookUserResponse getUserDataBySystemUserId(KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return authService.findUserDataBySystemUserId(systemUserId);
    }

}
