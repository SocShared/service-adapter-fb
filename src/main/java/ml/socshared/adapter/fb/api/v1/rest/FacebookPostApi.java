package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.util.UUID;

@Api(value = "Facebook Post API")
public interface FacebookPostApi {

    @ApiOperation(value = "Пост группы пользователя", notes = "Возвращает определенный пост по id")
    FacebookPostResponse getPost(String groupId, String postId, KeycloakAuthenticationToken token);

    @ApiOperation(value = "Посты группы пользователя", notes = "Возвращает посты определенной группы")
    Page<FacebookPostResponse> getPosts(String groupOrPageId, Integer page, Integer size, KeycloakAuthenticationToken token);

    @ApiOperation(value = "Добавление поста группы", notes = "Добавляет пост группы")
    FacebookPostResponse addPost(String groupId, FacebookPostRequest request, KeycloakAuthenticationToken token);

    @ApiOperation(value = "Обновление поста группы", notes = "Обновляет пост группы по id")
    FacebookPostResponse updatePost(String groupId, String postId, FacebookPostRequest request, KeycloakAuthenticationToken token);

    @ApiOperation(value = "Удаление поста группы", notes = "Удаляет пост группы по id")
    void deletePost(String groupId, String postId, KeycloakAuthenticationToken token);

}
