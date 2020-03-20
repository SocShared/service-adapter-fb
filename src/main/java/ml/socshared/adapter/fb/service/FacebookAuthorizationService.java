package ml.socshared.adapter.fb.service;

import org.springframework.http.HttpHeaders;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface FacebookAuthorizationService {

    String getAccess() throws IOException;

    AccessGrant getToken(UUID userId, String authorizationCode);

    Connection<Facebook> getConnection(AccessGrant accessGrant);

}
