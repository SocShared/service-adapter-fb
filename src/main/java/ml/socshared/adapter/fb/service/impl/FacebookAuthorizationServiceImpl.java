package ml.socshared.adapter.fb.service.impl;

import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Service
public class FacebookAuthorizationServiceImpl implements FacebookAuthorizationService {

    private FacebookConnectionFactory factory;

    public FacebookAuthorizationServiceImpl(FacebookConnectionFactory facebookConnectionFactory) {
        this.factory = facebookConnectionFactory;
    }

    @Override
    public void getAccess(HttpServletResponse response, HttpHeaders headers) throws IOException {

        OAuth2Operations operations = factory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri("http://fb.socshared.ml/api/v1/token");
        params.setScope("email,public_profile");

        String url = operations.buildAuthenticateUrl(params);
        System.out.println("The URL is" + url);
        response.sendRedirect(url);

    }

    @Override
    public AccessGrant getToken(String authorizationCode, HttpHeaders headers) {
        OAuth2Operations operations = factory.getOAuthOperations();
        return operations.exchangeForAccess(authorizationCode, "http://fb.socshared.ml/api/v1/token",
                null);
    }

    @Override
    public Connection<Facebook> getConnection(AccessGrant accessGrant) {
        return factory.createConnection(accessGrant);
    }
}
