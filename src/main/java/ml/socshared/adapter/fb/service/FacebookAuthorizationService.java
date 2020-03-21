package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;

import java.io.IOException;
import java.util.UUID;

public interface FacebookAuthorizationService {

    String getURLForAccess();
    FacebookUserResponse getToken(UUID systemUserId, String authorizationCode);
    Connection<Facebook> getConnection(AccessGrant accessGrant);
    FacebookUserResponse findUserDataBySystemUserId(UUID systemUserId);

}
