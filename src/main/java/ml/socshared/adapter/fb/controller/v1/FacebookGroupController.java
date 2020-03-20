package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.http.MediaType;
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
public class FacebookGroupController {

    private FacebookGroupService groupService;

    public FacebookGroupController(FacebookGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "/groups/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private PagedList<GroupMembership> getGroups(@PathVariable UUID userId) {
        return groupService.findByUserId(userId);
    }
}
