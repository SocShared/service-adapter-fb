package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupOperations;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<FacebookGroupResponse> findBySystemUserId(UUID systemUserId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant);

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        GroupOperations operations = faService.getConnection(accessGrant).getApi().groupOperations();

        operations.getMemberships().forEach(s -> {
            int memberCount = operations.getMembers(s.getId()).size();
            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setName(s.getName());
            response.setFacebookGroupId(s.getId());
            response.setIsSelected(false);
            response.setCountUsers(memberCount);
            facebookGroupResponseList.add(response);
        });

        log.info("Facebook Groups: size {}: {}", facebookGroupResponseList.size(), facebookGroupResponseList);

        return facebookGroupResponseList;
    }

    @Override
    public FacebookGroupResponse findBySystemUserIdAndGroupId(UUID systemUserId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant);

        GroupOperations operations = faService.getConnection(accessGrant).getApi().groupOperations();
        Group group = operations.getGroup(groupId);
        int memberCount = operations.getMembers(groupId).size();
        FacebookGroupResponse response = new FacebookGroupResponse();
        response.setIsSelected(false);
        response.setFacebookGroupId(groupId);
        response.setName(group.getName());
        response.setCountUsers(memberCount);
        log.info("Facebook Group: {}", response);

        return response;
    }


}
