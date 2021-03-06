package ml.socshared.adapter.fb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.response.AccountCountResponse;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.sentry.SentrySender;
import ml.socshared.adapter.fb.service.sentry.SentryTag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookAuthorizationServiceImpl implements FacebookAuthorizationService {

    @Value("${facebook.redirect.uri}")
    private String redirectUri;

    private final FacebookConnectionFactory factory;
    private final FacebookAccessGrantService fagService;
    private final SentrySender sentrySender;

    @Override
    public String getURLForAccess() {
        log.info("Init OAuth2.0");
        OAuth2Operations operations = factory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri(redirectUri);
        params.setScope("email,public_profile,publish_to_groups,groups_access_member_info,publish_pages,manage_pages,user_posts,read_insights");

        String url = operations.buildAuthenticateUrl(params);
        log.info("URL-redirect: {}", url);

        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("url", url);
        sentrySender.sentryMessage("url forming", objectHashMap, Collections.singletonList(SentryTag.URL_FORMING));

        return url;
    }

    @Override
    public SuccessResponse saveAccountFacebook(UUID systemUserId, String authorizationCode) {
        AccessGrant grant = factory.getOAuthOperations()
                .exchangeForAccess(authorizationCode, redirectUri, null);
        saveToken(systemUserId, grant);
        log.info("Token: {}", grant.getAccessToken());

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(true);

        return successResponse;
    }

    private void saveToken(UUID systemUserId, AccessGrant grant) {
        User user = getConnection(grant).getApi().fetchObject("me", User.class, "id");
        FacebookAccessGrant fbAccessGrant = new FacebookAccessGrant();
        fbAccessGrant.setSystemUserId(systemUserId);
        fbAccessGrant.setFacebookUserId(user.getId());
        fbAccessGrant.setAccessToken(grant.getAccessToken());
        fbAccessGrant.setExpireTime(grant.getExpireTime());
        fbAccessGrant.setRefreshToken(grant.getRefreshToken());
        fbAccessGrant.setScope(grant.getScope());

        if (fagService.save(fbAccessGrant) == null)
            throw new HttpBadRequestException("Not save token: " + systemUserId);
        log.info("Save Facebook Access Grant: {}", fbAccessGrant);
    }

    @Override
    @Cacheable("connection")
    public Connection<Facebook> getConnection(AccessGrant accessGrant) {
        Connection<Facebook> connFacebook = factory.createConnection(accessGrant);
        log.info("Connection Facebook: User Name - {}", connFacebook.getDisplayName());
        return connFacebook;
    }

    @Override
    @Cacheable("facebook_template")
    public FacebookTemplate getFacebookTemplate(String accessToken) {
        FacebookTemplate facebookTemplate = new FacebookTemplate(accessToken);
        facebookTemplate.setApiVersion("6.0");
        return facebookTemplate;
    }

    @Override
    public FacebookUserResponse findUserDataBySystemUserId(UUID systemUserId) {
        try {
            AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
            log.info("Token: {}", accessGrant.getAccessToken());
            User user = getConnection(accessGrant).getApi().fetchObject("me", User.class, "id", "email", "first_name", "last_name");
            log.info("User: {}", user.getId());
            FacebookUserResponse response = new FacebookUserResponse();
            response.setAccessToken(accessGrant.getAccessToken());
            response.setEmail(user.getEmail());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setSystemUserId(systemUserId);
            response.setAccountId(user.getId());
            response.setSocialNetwork("FB");
            log.info("Facebook User Response: {}", response);
            return response;
        } catch (HttpNotFoundException exc) {
            return null;
        }
    }

    @Override
    public void deleteFacebookAccount(UUID systemUserId) {
        fagService.deleteBySystemUserId(systemUserId);
    }

    @Override
    public AccountCountResponse count() {
        return fagService.count();
    }
}
