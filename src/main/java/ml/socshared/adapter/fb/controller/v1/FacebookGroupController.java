package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookGroupApi;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookGroupController implements FacebookGroupApi {

    private FacebookGroupService groupService;

    public FacebookGroupController(FacebookGroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    @GetMapping(value = "/users/{userId}/groups/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group getGroup(@PathVariable UUID userId, @PathVariable String groupId) {
        return groupService.findByUserIdAndGroupId(userId, groupId);
    }

    @Override
    @GetMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedList<GroupMembership> getGroups(@PathVariable UUID userId) {
        return groupService.findByUserId(userId);
    }
}
