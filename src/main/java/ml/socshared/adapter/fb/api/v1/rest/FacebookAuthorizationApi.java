package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.response.AccessUrlResponse;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import org.springframework.social.oauth2.AccessGrant;

import java.util.Map;
import java.util.UUID;

@Api(value = "Facebook Access Grant API")
public interface FacebookAuthorizationApi {

    @ApiOperation(value = "Данные пользователя с токеном", notes = "Возвращает JSON-объект, " +
            "содержащий данные пользователя и токен доступа, по id пользователя Системы.")
    FacebookUserResponse getUserDataBySystemUserId(UUID systemUserId);

    @ApiOperation(value = "Токена для доступа к API Facebook", notes = "Получение токена доступа для взаимодействия с API Facebook")
    FacebookUserResponse getTokenFacebook(UUID systemUserId, String authorizationCode);

    @ApiOperation(value = "URL-адрес для авторизации Facebook", notes = "Получение URL для авторизации в Facebook" +
            "с помощью OAuth2.0.")
    AccessUrlResponse getAccessUrl();

}
