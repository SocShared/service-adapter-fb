package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.FacebookAdminClientGroup;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.repository.FacebookClientGroupRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.GroupOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class FacebookGroupServiceImpl implements FacebookGroupService {

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;
    private FacebookClientGroupRepository groupRepository;

    public FacebookGroupServiceImpl(FacebookAuthorizationService faService,
                                    FacebookAccessGrantService fagService,
                                    FacebookClientGroupRepository groupRepository) {
        this.faService = faService;
        this.fagService = fagService;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<FacebookGroupResponse> findBySystemUserId(UUID systemUserId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());
        FacebookAccessGrant userResponse = fagService.findBySystemUserId(systemUserId);
        log.info("User: {}", userResponse.getFacebookUserId());

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        PagedList<TreeMap> groups = faService.getConnection(accessGrant).getApi()
                .fetchConnections(userResponse.getFacebookUserId(), "groups", TreeMap.class,
                        "id", "name", "administrator", "member_count");

        groups.forEach(s -> {
            if ((Boolean) s.get("administrator")) {
                FacebookGroupResponse response = new FacebookGroupResponse();
                response.setSystemUserId(systemUserId);
                response.setName((String) s.get("name"));
                response.setFacebookGroupId((String) s.get("id"));
                response.setIsSelected(false);
                response.setIsAdministrator((Boolean) s.get("administrator"));
                response.setMembersCount((Integer) s.get("member_count"));
                facebookGroupResponseList.add(response);
            }
        });

        facebookGroupResponseList.forEach(s -> {
            FacebookAdminClientGroup group = new FacebookAdminClientGroup();
            group.setFacebookAccessGrant(userResponse);
            group.setFacebookGroupId(s.getFacebookGroupId());
            groupRepository.save(group);
        });

        log.info("Facebook Groups: size {}: {}", facebookGroupResponseList.size(), facebookGroupResponseList);

        return facebookGroupResponseList;
    }

    @Override
    public FacebookGroupResponse findBySystemUserIdAndGroupId(UUID systemUserId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        groupRepository.findById(groupId).orElseThrow(() -> new HttpNotFoundException("Not found group at the db"));


        TreeMap group = faService.getConnection(accessGrant).getApi().fetchObject(groupId, TreeMap.class,
                "id", "name", "member_count");

        FacebookGroupResponse response = new FacebookGroupResponse();
        response.setIsSelected(false);
        response.setIsAdministrator(true);
        response.setFacebookGroupId(groupId);
        response.setSystemUserId(systemUserId);
        response.setName((String) group.get("name"));
        response.setMembersCount((Integer) group.get("member_count"));
        log.info("Facebook Group: {}", response);

        return response;
    }


}
