package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;

import java.util.UUID;

public interface FacebookGroupService {

    FacebookGroupResponse findGroupBySystemUserIdAndGroupId(UUID systemUserId, String groupId);
    FacebookGroupResponse findPageBySystemUserIdAndPageId(UUID systemUserId, String pageId);
    Page<FacebookGroupResponse> findGroupsBySystemUserId(UUID systemUserId, Integer page, Integer size);
    Page<FacebookGroupResponse> findPagesBySystemUserId(UUID systemUserId, Integer page, Integer size);

}
