package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;

import java.util.List;
import java.util.UUID;

@Api(value = "Facebook Group API")
public interface FacebookGroupApi {

    @ApiOperation(value = "Группа пользователя", notes = "Возвращает группу пользователя " +
            "по id пользователя и по id группы")
    FacebookGroupResponse getGroup(UUID systemUserId, String groupId);

    @ApiOperation(value = "Группы пользователя", notes = "Возвращает группы пользователя " +
            "по id пользователя")
    List<FacebookGroupResponse> getGroups(UUID userId);

}
