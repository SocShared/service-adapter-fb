package ml.socshared.adapter.fb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.group.TypeGroup;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import ml.socshared.adapter.fb.service.sentry.SentrySender;
import ml.socshared.adapter.fb.service.sentry.SentryTag;
import ml.socshared.adapter.fb.service.util.GroupBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookGroupServiceImpl implements FacebookGroupService {

    @Value("${facebook.adapter.id}")
    private String adapterId;

    @Value("${cache.pages}")
    private final String pages = "pages";

    private final FacebookAuthorizationService faService;
    private final FacebookAccessGrantService fagService;
    private final GroupBuffer groupBuffer;
    private final SentrySender sentrySender;

    @Override
    public Page<FacebookGroupResponse> findGroupsBySystemUserId(UUID systemUserId, Integer page, Integer size) {

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        Page<FacebookGroupResponse> pageResponse = new Page<>();
        pageResponse.setHasPrev(false);
        pageResponse.setHasNext(false);

        PagedList<Map> groups = groupBuffer.getBufferGroups(systemUserId, page * size / 100);

        int i, j;
        for (i = page * size, j = 0; i < groups.size() && j < size; i++, j++) {
            Map map = groups.get(i);

            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) map.get("id"));
            response.setName((String) map.get("name"));
            response.setAdapterId(adapterId);
            response.setMembersCount((Integer) map.get("member_count"));
            response.setType(TypeGroup.FB_GROUP);
            facebookGroupResponseList.add(response);
        }
        pageResponse.setHasNext(i < groups.size());

        log.info("Facebook Groups: size {}: {}", facebookGroupResponseList.size(), facebookGroupResponseList);

        pageResponse.setObjects(facebookGroupResponseList);
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setHasPrev(page != 0);

        Map<String, Object> sentryMap = new HashMap<>();
        sentryMap.put("system_user_id", systemUserId);
        sentrySender.sentryMessage("get groups of user", sentryMap, Collections.singletonList(SentryTag.GET_USER_GROUPS));

        return pageResponse;
    }

    @Override
    @Cacheable(pages)
    public Page<FacebookGroupResponse> findPagesBySystemUserId(UUID systemUserId, Integer page, Integer size) {

        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());
        FacebookAccessGrant userResponse = fagService.findBySystemUserId(systemUserId);
        log.info("User: {}", userResponse.getFacebookUserId());

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        Page<FacebookGroupResponse> pageResponse = new Page<>();
        pageResponse.setHasPrev(false);
        pageResponse.setHasNext(false);

        MultiValueMap<String, String> pageParamMap = new LinkedMultiValueMap<>();
        pageParamMap.put("fields", Collections.singletonList("id,name,fan_count,tasks"));
        pageParamMap.put("limit", Collections.singletonList(size + ""));
        pageParamMap.put("offset", Collections.singletonList(page * size + ""));

        PagedList<Map> pages = faService.getConnection(accessGrant).getApi()
                .fetchConnections(userResponse.getFacebookUserId(), "accounts", Map.class, pageParamMap);

        pages.forEach(s -> {
            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) s.get("id"));
            response.setName((String) s.get("name"));
            response.setAdapterId(adapterId);
            response.setMembersCount((Integer) s.get("fan_count"));
            response.setType(TypeGroup.FB_PAGE);
            facebookGroupResponseList.add(response);
        });

        log.info("Facebook Groups: size {}: {}", facebookGroupResponseList.size(), facebookGroupResponseList);

        pageResponse.setObjects(facebookGroupResponseList);
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setHasPrev(pages.getPreviousPage() != null && page != 0);
        pageResponse.setHasNext(pages.getNextPage() != null && facebookGroupResponseList.size() == size);

        Map<String, Object> sentryMap = new HashMap<>();
        sentryMap.put("system_user_id", systemUserId);
        sentrySender.sentryMessage("get pages of user", sentryMap, Collections.singletonList(SentryTag.GET_USER_GROUPS));

        return pageResponse;
    }

    @Override
    public FacebookGroupResponse findGroupBySystemUserIdAndGroupId(UUID systemUserId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        Map group = faService.getConnection(accessGrant).getApi().fetchObject(groupId, Map.class,
                "id", "name", "member_count", "created_time", "updated_time");

        if (group.get("member_count") == null)
            throw new HttpNotFoundException("Not found group by id: " + groupId);

        FacebookGroupResponse response = new FacebookGroupResponse();
        response.setSystemUserId(systemUserId);
        response.setGroupId((String) group.get("id"));
        response.setName((String) group.get("name"));
        response.setAdapterId(adapterId);
        response.setMembersCount((Integer) group.get("member_count"));
        response.setType(TypeGroup.FB_GROUP);

        log.info("Facebook Group: {}", response);

        Map<String, Object> sentryMap = new HashMap<>();
        sentryMap.put("system_user_id", systemUserId);
        sentryMap.put("group_id", groupId);
        sentrySender.sentryMessage("get group of user", sentryMap, Collections.singletonList(SentryTag.GET_USER_GROUP));

        return response;
    }

    @Override
    public FacebookGroupResponse findPageBySystemUserIdAndPageId(UUID systemUserId, String pageId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class,
                    "id", "name", "talking_about_count");

            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) page.get("id"));
            response.setName((String) page.get("name"));
            response.setAdapterId(adapterId);
            response.setMembersCount((Integer) page.get("talking_about_count"));
            response.setType(TypeGroup.FB_PAGE);

            log.info("Facebook Group: {}", response);

            Map<String, Object> sentryMap = new HashMap<>();
            sentryMap.put("system_user_id", systemUserId);
            sentryMap.put("page_id", pageId);
            sentrySender.sentryMessage("get page of user", sentryMap, Collections.singletonList(SentryTag.GET_USER_GROUP));

            return response;

        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }
}
