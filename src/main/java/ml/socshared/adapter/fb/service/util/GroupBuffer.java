package ml.socshared.adapter.fb.service.util;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class GroupBuffer {

    @Value("${cache.groups}")
    private final String groups = "groups";

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public GroupBuffer(FacebookAuthorizationService faService,
                                    FacebookAccessGrantService fagService) {
        this.faService = faService;
        this.fagService = fagService;
    }

    @Cacheable(groups)
    public PagedList<Map> getBufferGroups(UUID systemUserId, Integer countPage) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());
        FacebookAccessGrant userResponse = fagService.findBySystemUserId(systemUserId);
        log.info("User: {}", userResponse.getFacebookUserId());

        MultiValueMap<String, String> groupParamMap = new LinkedMultiValueMap<>();
        groupParamMap.put("fields", Collections.singletonList("id,name,member_count,created_time,updated_time"));
        groupParamMap.put("limit", Collections.singletonList(100 + ""));
        groupParamMap.put("admin_only", Collections.singletonList(Boolean.TRUE.toString()));

        PagedList<Map> groups;
        groups = faService.getConnection(accessGrant).getApi()
                .fetchConnections(userResponse.getFacebookUserId(), "groups", Map.class, groupParamMap);

        int i = 0;
        while (groups.getNextPage() != null && i < countPage) {
            groupParamMap.put("after", Collections.singletonList(groups.getNextPage().getAfter()));
            groups = faService.getConnection(accessGrant).getApi()
                    .fetchConnections(userResponse.getFacebookUserId(), "groups", Map.class, groupParamMap);
            i++;
        }

        return groups;
    }

}
