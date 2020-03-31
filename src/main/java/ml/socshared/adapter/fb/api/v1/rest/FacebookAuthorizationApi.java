package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.response.FacebookUserResponse;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.social.oauth2.AccessGrant;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Api(value = "Facebook Access Grant API")
public interface FacebookAuthorizationApi {

    @ApiOperation(value = "Данные пользователя с токеном", notes = "Возвращает JSON-объект, " +
            "содержащий данные пользователя и токен доступа, по id пользователя Системы.")
    FacebookUserResponse getUserDataBySystemUserId(KeycloakAuthenticationToken token);

    @ApiOperation(value = "Токена для доступа к API Facebook", notes = "Получение токена доступа для взаимодействия с API Facebook")
    FacebookUserResponse getTokenFacebook(String authorizationCode, KeycloakAuthenticationToken token);

    @ApiOperation(value = "URL-адрес для авторизации Facebook", notes = "Получение URL для авторизации в Facebook" +
            "с помощью OAuth2.0.")
    Map<String, String> getAccessUrl(KeycloakAuthenticationToken token);

}
