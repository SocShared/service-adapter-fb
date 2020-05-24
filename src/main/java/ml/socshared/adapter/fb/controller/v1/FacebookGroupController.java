package ml.socshared.adapter.fb.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.adapter.fb.api.v1.rest.FacebookGroupApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookSelectGroupRequest;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookGroupService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
@RequiredArgsConstructor
public class FacebookGroupController implements FacebookGroupApi {

    private final FacebookGroupService groupService;

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{pageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookGroupResponse getGroup(@PathVariable UUID systemUserId, @PathVariable String pageId) {

        return groupService.findPageBySystemUserIdAndPageId(systemUserId, pageId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookGroupResponse> getGroups(@PathVariable UUID systemUserId,
                                                 @NotNull @RequestParam(name = "page", required = false) Integer page,
                                                 @NotNull @RequestParam(name = "size", required = false) Integer size) {

        return groupService.findPagesBySystemUserId(systemUserId, page, size);
    }

    @Override
    @PostMapping(value = "/users/{systemUserId}/groups/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                        produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse selectGroup(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                       @RequestBody FacebookSelectGroupRequest request) {

        return groupService.selectPage(systemUserId, groupId, request.getIsSelected());
    }
}
