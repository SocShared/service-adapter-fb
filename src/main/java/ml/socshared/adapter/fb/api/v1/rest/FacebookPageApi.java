package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.response.FacebookPageResponse;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PagedList;

import java.util.List;
import java.util.UUID;

@Api(value = "Facebook Page API")
public interface FacebookPageApi {

    @ApiOperation(value = "Список страниц пользователя", notes = "Получение списка страниц" +
            " пользователя по id пользователя")
    List<FacebookPageResponse> getAccounts(UUID systemUserId);
}
