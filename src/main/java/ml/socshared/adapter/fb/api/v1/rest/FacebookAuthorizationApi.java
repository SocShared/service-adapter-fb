package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import org.springframework.social.oauth2.AccessGrant;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Api(value = "Facebook Access Grant API")
public interface FacebookAuthorizationApi {

    @ApiOperation(value = "Данные пользователя с токеном", notes = "Возвращает JSON-объект, " +
            "содержащий данные пользователя и токен доступа, по id пользователя Системы.")
    FacebookUserResponse getUserDataByUserId(UUID userId);

    @ApiIgnore
    AccessGrant getToken(String authorizationCode);

    @ApiOperation(value = "Перенаправление на страницу Facebook", notes = "Получение доступа к Facebook для Системы " +
            "с помощью авторизации по OAuth2.0.")
    void getAccess(UUID userId, HttpServletResponse response) throws Exception;

}
