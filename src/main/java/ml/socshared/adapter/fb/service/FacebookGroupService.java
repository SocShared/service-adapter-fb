package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FacebookGroupService {

    FacebookGroupResponse findGroupBySystemUserIdAndGroupId(UUID systemUserId, String groupId);
    FacebookGroupResponse findPageBySystemUserIdAndPageId(UUID systemUserId, String pageId);
    Page<FacebookGroupResponse> findGroupsBySystemUserId(UUID systemUserId, Integer page, Integer size);
    Page<FacebookGroupResponse> findPagesBySystemUserId(UUID systemUserId, Integer page, Integer size);
    SuccessResponse selectPage(UUID systemUserId, String groupOrPageId, Boolean isSelect);

}
