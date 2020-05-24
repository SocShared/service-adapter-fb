package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookCommentApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class FacebookCommentController implements FacebookCommentApi {

    private FacebookCommentService commentService;

    public FacebookCommentController(FacebookCommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookCommentResponse getCommentOfPost(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                    @PathVariable String postId, @PathVariable String commentId) {

        return commentService.findCommentOfPostByCommentId(systemUserId, groupId, postId, commentId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookCommentResponse> getCommentsByPostId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                             @PathVariable String postId, @NotNull @RequestParam(value = "page", required = false) Integer page,
                                                             @NotNull @RequestParam(value = "size", required = false) Integer size) {

        return commentService.findCommentsOfPost(systemUserId, groupId, postId, page, size);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}/super_comments/{superCommentId}/comments/{commentId}")
    public FacebookCommentResponse getCommentOfSuperComment(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                                            @PathVariable String superCommentId, @PathVariable String commentId) {

        return commentService.findCommentOfSuperCommentByCommentId(systemUserId, groupId, postId, superCommentId, commentId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}/super_comments/{superCommentId}/comments")
    public Page<FacebookCommentResponse> getCommentsBySuperCommentId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                                     @PathVariable String postId, @PathVariable String superCommentId,
                                                                     @NotNull @RequestParam(value = "page", required = false) Integer page,
                                                                     @NotNull @RequestParam(value = "size", required = false) Integer size) {

        return commentService.findCommentsOfSuperComment(systemUserId, groupId, postId, superCommentId, page, size);
    }
}
