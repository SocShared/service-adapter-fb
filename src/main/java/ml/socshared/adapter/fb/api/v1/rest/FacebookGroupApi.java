package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookSelectGroupRequest;
import ml.socshared.adapter.fb.domain.response.FacebookGroupResponse;
import ml.socshared.adapter.fb.domain.response.SuccessResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Api(value = "Facebook Group API")
public interface FacebookGroupApi {

    @ApiOperation(value = "Группа пользователя", notes = "Возвращает группу пользователя " +
            "по id пользователя и по id группы")
    FacebookGroupResponse getGroup(UUID systemUserId, String groupId);

    @ApiOperation(value = "Группы пользователя, в которых является админом", notes = "Возвращает группы пользователя " +
            "по id пользователя, в которых он является админом")
    Page<FacebookGroupResponse> getGroups(UUID systemUserId,
                                          @Min(0) @NotNull Integer page,
                                          @NotNull @Min(0) @Max(100) Integer size);

}
