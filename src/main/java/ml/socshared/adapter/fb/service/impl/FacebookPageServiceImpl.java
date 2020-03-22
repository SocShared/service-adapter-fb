package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.response.FacebookPageResponse;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookPageService;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FacebookPageServiceImpl implements FacebookPageService {

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public FacebookPageServiceImpl(FacebookAuthorizationService faService,
                                   FacebookAccessGrantService fagService) {
        this.fagService = fagService;
        this.faService = faService;
    }

    @Override
    public List<FacebookPageResponse> findBySystemUserId(UUID systemUserId) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        PageOperations operations = faService.getConnection(grant).getApi().pageOperations();
        List<FacebookPageResponse> facebookPageResponseList = new LinkedList<>();

        operations.getAccounts().forEach(s -> {
            FacebookPageResponse response = new FacebookPageResponse();
            response.setAccessToken(s.getAccessToken());
            response.setCategory(s.getCategory());
            response.setFacebookPageId(s.getId());
            response.setName(s.getName());
            response.setPermissions(s.getPermissions());
            response.setSystemUserId(systemUserId);
            facebookPageResponseList.add(response);
        });

        log.info("Facebook Pages: size: {}: {}", facebookPageResponseList.size(), facebookPageResponseList);

        return facebookPageResponseList;
    }
}
