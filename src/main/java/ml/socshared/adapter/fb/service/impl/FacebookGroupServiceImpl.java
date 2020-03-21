package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.data.domain.Page;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FacebookGroupServiceImpl implements FacebookGroupService {

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public FacebookGroupServiceImpl(FacebookAuthorizationService faService,
                                    FacebookAccessGrantService fagService) {
        this.faService = faService;
        this.fagService = fagService;
    }

    @Override
    public PagedList<GroupMembership> findByUserId(UUID userId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(accessGrant).getApi().groupOperations().getMemberships();
    }

    @Override
    public Group findByUserIdAndGroupId(UUID userId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(accessGrant).getApi().groupOperations().getGroup(groupId);
    }
}
