package ml.socshared.adapter.fb.service.impl;

import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;


@Service
public class FacebookAuthorizationServiceImpl implements FacebookAuthorizationService {

    @Value("${facebook.redirect.uri}")
    private String redirectUri;

    private FacebookConnectionFactory factory;
    private FacebookAccessGrantService fagService;

    public FacebookAuthorizationServiceImpl(FacebookConnectionFactory facebookConnectionFactory,
                                            FacebookAccessGrantService fagService) {
        this.factory = facebookConnectionFactory;
        this.fagService = fagService;
    }

    @Override
    public String getAccess() throws IOException {

        OAuth2Operations operations = factory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri(redirectUri);
        params.setScope("email,public_profile,publish_to_groups,groups_access_member_info,publish_pages,manage_pages,user_posts");

        String url = operations.buildAuthenticateUrl(params);
        System.out.println("The URL is" + url);

        return url;
    }

    @Override
    public AccessGrant getToken(UUID userId, String authorizationCode) {
        AccessGrant grant = factory.getOAuthOperations()
                .exchangeForAccess(authorizationCode, redirectUri, null);
        if (!saveToken(userId, grant))
            throw new RuntimeException("Not save token: " + userId);
        return grant;
    }

    private boolean saveToken(UUID userId, AccessGrant grant) {
        FacebookAccessGrant fbAccessGrant = new FacebookAccessGrant();
        fbAccessGrant.setUserId(userId);
        fbAccessGrant.setAccessToken(grant.getAccessToken());
        fbAccessGrant.setExpireTime(grant.getExpireTime());
        fbAccessGrant.setRefreshToken(grant.getRefreshToken());
        fbAccessGrant.setScope(grant.getScope());
        return fagService.save(fbAccessGrant) != null;
    }

    @Override
    public Connection<Facebook> getConnection(AccessGrant accessGrant) {
        return factory.createConnection(accessGrant);
    }

    @Override
    public FacebookUserResponse findUserDataById(UUID id) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(id).getAccessToken());
        User user = getConnection(accessGrant).getApi().fetchObject("me", User.class, "id", "email", "first_name", "last_name");
        FacebookUserResponse request = new FacebookUserResponse();
        request.setAccessToken(accessGrant.getAccessToken());
        request.setEmail(user.getEmail());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        request.setUserId(id);
        return request;
    }
}
