package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookPageService;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

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
    public PagedList<Account> findByUserId(UUID userId) {
        AccessGrant grant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(grant).getApi().pageOperations().getAccounts();
    }
}
