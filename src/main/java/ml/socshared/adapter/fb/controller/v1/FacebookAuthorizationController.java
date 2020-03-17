package ml.socshared.adapter.fb.controller.v1;

import io.swagger.annotations.ResponseHeader;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class FacebookAuthorizationController {

    private FacebookAuthorizationService authService;

    public FacebookAuthorizationController(FacebookAuthorizationService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/access")
    public void producer(HttpServletResponse response, @RequestHeader HttpHeaders headers) throws Exception {
        authService.getAccess(response, headers);
    }

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    private AccessGrant getToken(@RequestParam("code") String authorizationCode,
                          @RequestHeader HttpHeaders headers) {

        /*Facebook facebook = connection.getApi();
        String[] fields = { "id", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);
        return userProfile;*/
        return authService.getToken(authorizationCode, headers);
    }


}
