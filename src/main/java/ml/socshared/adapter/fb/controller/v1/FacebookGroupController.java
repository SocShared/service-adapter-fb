package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookGroupApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookSelectGroupRequest;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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
        return groupService.findGroupBySystemUserIdAndGroupId(systemUserId, groupId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookGroupResponse> getGroups(@PathVariable UUID systemUserId,
                                                 @RequestParam(name = "page", required = false) Integer page,
                                                 @RequestParam(name = "size", required = false) Integer size) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        return groupService.findGroupsBySystemUserId(systemUserId, page, size);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/pages/{pageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookGroupResponse getPage(@PathVariable UUID systemUserId, @PathVariable String pageId) {
        return groupService.findPageBySystemUserIdAndPageId(systemUserId, pageId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/pages", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookGroupResponse> getPages(@PathVariable UUID systemUserId,
                                                 @RequestParam(name = "page", required = false) Integer page,
                                                 @RequestParam(name = "size", required = false) Integer size) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        return groupService.findPagesBySystemUserId(systemUserId, page, size);
    }

    @Override
    @PostMapping(value = "/users/{systemUserId}/groups/{groupOrPageId}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                        produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Boolean> selectGroupOrPage(@PathVariable UUID systemUserId,
                                                  @PathVariable String groupOrPageId,
                                                  @RequestBody FacebookSelectGroupRequest request) {
        return groupService.selectGroupOrPage(systemUserId, groupOrPageId, request.getIsSelected());
    }
}
