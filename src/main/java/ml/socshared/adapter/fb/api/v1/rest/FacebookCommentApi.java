package ml.socshared.adapter.fb.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Api(value = "Facebook Comment API")
public interface FacebookCommentApi {

    @ApiOperation(value = "Комментарий поста группы пользователя", notes = "Возвращает определенный комментарий по id")
    FacebookCommentResponse getCommentOfPost(UUID systemUserId, String groupId, String postId, String commentId);

    @ApiOperation(value = "Комменатрии поста группы пользователя", notes = "Возвращает комментарии определенного поста")
    Page<FacebookCommentResponse> getCommentsByPostId(UUID systemUserId, String groupId, String postId,
                                                      @Min(0) @NotNull Integer page,
                                                      @Min(0) @Max(100) @NotNull Integer size);

    @ApiOperation(value = "Комментарий суперкомментария поста группы пользователя", notes = "Возвращает определенный комментарий по id")
    FacebookCommentResponse getSubCommentOfComment(UUID systemUserId, String groupId, String postId, String commentId, String subCommentId);

    @ApiOperation(value = "Комменатрии суперкомментария поста группы пользователя", notes = "Возвращает комментарии определенного суперкомментария")
    Page<FacebookCommentResponse> getSubCommentsByCommentId(UUID systemUserId, String groupId, String postId, String commentId,
                                                            @Min(0) @NotNull Integer page,
                                                            @Min(0) @Max(100) @NotNull Integer size);

}
