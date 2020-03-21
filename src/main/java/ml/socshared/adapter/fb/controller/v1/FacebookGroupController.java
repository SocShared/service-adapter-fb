package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookGroupApi;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.http.MediaType;
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
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookGroupResponse getGroup(@PathVariable UUID systemUserId, @PathVariable String groupId) {
        return groupService.findBySystemUserIdAndGroupId(systemUserId, groupId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FacebookGroupResponse> getGroups(@PathVariable UUID systemUserId) {
        return groupService.findBySystemUserId(systemUserId);
    }
}
