package ml.socshared.adapter.fb.service;

import org.springframework.http.HttpHeaders;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FacebookAuthorizationService {

    void getAccess(HttpServletResponse response, HttpHeaders headers) throws IOException;

    AccessGrant getToken(String authorizationCode, HttpHeaders headers);

    Connection<Facebook> getConnection(AccessGrant accessGrant);

}
