package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.oauth2.AccessGrant;

import java.util.List;
import java.util.UUID;

public interface FacebookGroupService {

    Group findByUserIdAndGroupId(UUID userId, String groupId);
    PagedList<GroupMembership> findByUserId(UUID userId);

}
