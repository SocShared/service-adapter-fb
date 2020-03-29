package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookSelectGroupRequest;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(value = "Facebook Group API")
public interface FacebookGroupApi {

    @ApiOperation(value = "Группа пользователя", notes = "Возвращает группу пользователя " +
            "по id пользователя и по id группы")
    FacebookGroupResponse getGroup(UUID systemUserId, String groupId);

    @ApiOperation(value = "Страница пользователя", notes = "Возвращает страницу пользователя " +
            "по id пользователя и по id страницы")
    FacebookGroupResponse getPage(UUID systemUserId, String pageId);

    @ApiOperation(value = "Изменение группы на выбранную или нет", notes = "Возвращает группу или страницу ")
    Map<String, Boolean> selectGroupOrPage(UUID systemUserId, String groupOrPageId, FacebookSelectGroupRequest request);

    @ApiOperation(value = "Группы пользователя, в которых является админом", notes = "Возвращает группы пользователя " +
            "по id пользователя, в которых он является админом")
    Page<FacebookGroupResponse> getGroups(UUID userId, Integer page, Integer size);

    @ApiOperation(value = "Страницы пользователя", notes = "Возвращает страницы пользователя " +
            "по id пользователя, в которых он является админом")
    Page<FacebookGroupResponse> getPages(UUID userId, Integer page, Integer size);

}
