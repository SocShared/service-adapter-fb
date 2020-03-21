package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;

import java.util.UUID;

@Api(value = "Facebook Group API")
public interface FacebookGroupApi {

    @ApiOperation(value = "Группа пользователя", notes = "Возвращает группу пользователя " +
            "по id пользователя и по id группы")
    Group getGroup(UUID userId, String groupId);

    @ApiOperation(value = "Группы пользователя", notes = "Возвращает группы пользователя " +
            "по id пользователя")
    PagedList<GroupMembership> getGroups(UUID userId);

}
