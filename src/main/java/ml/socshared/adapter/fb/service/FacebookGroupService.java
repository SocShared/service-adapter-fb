package ml.socshared.adapter.fb.service;

import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.oauth2.AccessGrant;

import java.util.List;
import java.util.UUID;

public interface FacebookGroupService {

    List<GroupMembership> findByUserId(UUID userId);

}
