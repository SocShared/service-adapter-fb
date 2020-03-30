package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;

import java.util.UUID;

@Api(value = "Facebook Comment API")
public interface FacebookCommentApi {

    @ApiOperation(value = "Комментарий поста группы пользователя", notes = "Возвращает определенный комментарий по id")
    FacebookCommentResponse getCommentOfPost(UUID systemUserId, String groupId, String postId, String commentId);

    @ApiOperation(value = "Комменатрии поста группы пользователя", notes = "Возвращает комментарии определенного поста")
    Page<FacebookCommentResponse> getCommentsByPostId(UUID systemUserId, String groupId, String postId, Integer page, Integer size);

    @ApiOperation(value = "Комментарий суперкомментария поста группы пользователя", notes = "Возвращает определенный комментарий по id")
    FacebookCommentResponse getCommentOfSuperComment(UUID systemUserId, String groupId, String postId, String superCommentId, String commentId);

    @ApiOperation(value = "Комменатрии суперкомментария поста группы пользователя", notes = "Возвращает комментарии определенного суперкомментария")
    Page<FacebookCommentResponse> getCommentsBySuperCommentId(UUID systemUserId, String groupId, String postId, String superCommentId, Integer page, Integer size);

}
