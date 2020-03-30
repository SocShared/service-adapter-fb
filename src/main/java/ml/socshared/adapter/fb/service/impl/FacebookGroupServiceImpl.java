package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.FacebookAdminClientGroup;
import ml.socshared.adapter.fb.domain.group.TypeGroup;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.repository.FacebookAdminClientGroupRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import ml.socshared.adapter.fb.service.util.GroupBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class FacebookGroupServiceImpl implements FacebookGroupService {

    @Value("${facebook.adapter.id}")
    private String adapterId;

    @Value("${cache.pages}")
    private final String pages = "pages";

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;
    private FacebookAdminClientGroupRepository groupRepository;
    private GroupBuffer groupBuffer;

    public FacebookGroupServiceImpl(FacebookAuthorizationService faService,
                                    FacebookAccessGrantService fagService,
                                    FacebookAdminClientGroupRepository groupRepository,
                                    GroupBuffer groupBuffer) {
        this.faService = faService;
        this.fagService = fagService;
        this.groupRepository = groupRepository;
        this.groupBuffer = groupBuffer;
    }

    @Override
    public Page<FacebookGroupResponse> findGroupsBySystemUserId(UUID systemUserId, Integer page, Integer size) {
        if (page < 0)
            throw new HttpBadRequestException(String.format("Error: page parameter contains invalid value (%d)", page));
        if (size < 0)
            throw new HttpBadRequestException(String.format("Error: size parameter contains invalid value (%d)", size));
        if (size > 100)
            throw new HttpBadRequestException(String.format("Error: the maximum value of size parameter is 100 (%d)", size));

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        Page<FacebookGroupResponse> pageResponse = new Page<>();
        pageResponse.setHasPrev(false);
        pageResponse.setHasNext(false);

        PagedList<TreeMap> groups = groupBuffer.getBufferGroups(systemUserId, page * size / 100);

        int i, j;
        for (i = page * size, j = 0; i < groups.size() && j < size; i++, j++) {
            TreeMap map = groups.get(i);

            boolean isSelect = groupRepository.findById((String) map.get("id")).orElse(null) != null;
            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) map.get("id"));
            response.setName((String) map.get("name"));
            response.setAdapterId(adapterId);
            response.setIsSelected(isSelect);
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

        return pageResponse;
    }

    @Override
    @Cacheable(pages)
    public Page<FacebookGroupResponse> findPagesBySystemUserId(UUID systemUserId, Integer page, Integer size) {
        if (page < 0)
            throw new HttpBadRequestException(String.format("Error: page parameter contains invalid value (%d)", page));
        if (size < 0)
            throw new HttpBadRequestException(String.format("Error: size parameter contains invalid value (%d)", size));
        if (size > 100)
            throw new HttpBadRequestException(String.format("Error: the maximum value of size parameter is 100 (%d)", size));

        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());
        FacebookAccessGrant userResponse = fagService.findBySystemUserId(systemUserId);
        log.info("User: {}", userResponse.getFacebookUserId());

        List<FacebookGroupResponse> facebookGroupResponseList = new LinkedList<>();
        Page<FacebookGroupResponse> pageResponse = new Page<>();
        pageResponse.setHasPrev(false);
        pageResponse.setHasNext(false);

        MultiValueMap<String, String> pageParamMap = new LinkedMultiValueMap<>();
        pageParamMap.put("fields", Collections.singletonList("id,name,talking_about_count,tasks"));
        pageParamMap.put("limit", Collections.singletonList(size + ""));
        pageParamMap.put("offset", Collections.singletonList(page * size + ""));

        PagedList<TreeMap> pages = faService.getConnection(accessGrant).getApi()
                .fetchConnections(userResponse.getFacebookUserId(), "accounts", TreeMap.class, pageParamMap);

        pages.forEach(s -> {
            boolean isSelect = groupRepository.findById((String) s.get("id")).orElse(null) != null;
            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) s.get("id"));
            response.setName((String) s.get("name"));
            response.setAdapterId(adapterId);
            response.setIsSelected(isSelect);
            response.setMembersCount((Integer) s.get("talking_about_count"));
            response.setType(TypeGroup.FB_PAGE);
            facebookGroupResponseList.add(response);
        });

        log.info("Facebook Groups: size {}: {}", facebookGroupResponseList.size(), facebookGroupResponseList);

        pageResponse.setObjects(facebookGroupResponseList);
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setHasPrev(pages.getPreviousPage() != null && page != 0);
        pageResponse.setHasNext(pages.getNextPage() != null && facebookGroupResponseList.size() == size);

        return pageResponse;
    }

    @Override
    public FacebookGroupResponse findGroupBySystemUserIdAndGroupId(UUID systemUserId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        TreeMap group = faService.getConnection(accessGrant).getApi().fetchObject(groupId, TreeMap.class,
                "id", "name", "member_count", "created_time", "updated_time");

        if (group.get("member_count") == null)
            throw new HttpNotFoundException("Not found group by id: " + groupId);

        boolean isSelect = groupRepository.findById((String) group.get("id")).orElse(null) != null;
        FacebookGroupResponse response = new FacebookGroupResponse();
        response.setSystemUserId(systemUserId);
        response.setGroupId((String) group.get("id"));
        response.setName((String) group.get("name"));
        response.setAdapterId(adapterId);
        response.setIsSelected(isSelect);
        response.setMembersCount((Integer) group.get("member_count"));
        response.setType(TypeGroup.FB_GROUP);

        log.info("Facebook Group: {}", response);

        return response;
    }

    @Override
    public FacebookGroupResponse findPageBySystemUserIdAndPageId(UUID systemUserId, String pageId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            TreeMap page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, TreeMap.class,
                    "id", "name", "talking_about_count");

            boolean isSelect = groupRepository.findById((String) page.get("id")).orElse(null) != null;
            FacebookGroupResponse response = new FacebookGroupResponse();
            response.setSystemUserId(systemUserId);
            response.setGroupId((String) page.get("id"));
            response.setName((String) page.get("name"));
            response.setAdapterId(adapterId);
            response.setIsSelected(isSelect);
            response.setMembersCount((Integer) page.get("talking_about_count"));
            response.setType(TypeGroup.FB_PAGE);

            log.info("Facebook Group: {}", response);

            return response;

        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    public Map<String, Boolean> selectPage(UUID systemUserId, String pageId, Boolean isSelect) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            TreeMap page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, TreeMap.class, "id", "talking_about_count");

            if (page != null && page.get("talking_about_count") != null) {
                FacebookAdminClientGroup p = new FacebookAdminClientGroup();
                p.setFacebookAccessGrant(fagService.findBySystemUserId(systemUserId));
                p.setFacebookGroupId((String) page.get("id"));
                p.setType(TypeGroup.FB_PAGE);

                groupRepository.save(p);
                return new HashMap<>() {
                    {
                        put("success", true);
                    }
                };
            }
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }

        return new HashMap<>() {
            {
                put("success", false);
            }
        };
    }
}
