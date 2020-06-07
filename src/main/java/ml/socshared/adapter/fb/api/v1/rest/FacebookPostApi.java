package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Api(value = "Facebook Post API")
public interface FacebookPostApi {

    @ApiOperation(value = "Пост группы пользователя", notes = "Возвращает определенный пост по id")
    FacebookPostResponse getPost(UUID systemUserId, String groupId, String postId);

    @ApiOperation(value = "Посты группы пользователя", notes = "Возвращает посты определенной группы")
    Page<FacebookPostResponse> getPosts(UUID systemUserId, String groupOrPageId,
                                        @Min(0) @NotNull Integer page,
                                        @Min(0) @Max(100) @NotNull Integer size);

    @ApiOperation(value = "Добавление поста группы", notes = "Добавляет пост группы")
    FacebookPostResponse addPost(UUID systemUserId, String groupId, FacebookPostRequest request);

    @ApiOperation(value = "Обновление поста группы", notes = "Обновляет пост группы по id")
    FacebookPostResponse updatePost(UUID systemUserId, String groupId, String postId, FacebookPostRequest request);

    @ApiOperation(value = "Удаление поста группы", notes = "Удаляет пост группы по id")
    void deletePost(UUID systemUserId, String groupId, String postId);

}
