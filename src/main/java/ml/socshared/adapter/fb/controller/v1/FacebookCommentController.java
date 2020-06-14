package ml.socshared.adapter.fb.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.adapter.fb.api.v1.rest.FacebookCommentApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FacebookCommentController implements FacebookCommentApi {

    private final FacebookCommentService commentService;

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}")
    public FacebookCommentResponse getCommentOfPost(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                    @PathVariable String postId, @PathVariable String commentId) {

        return commentService.findCommentOfPostByCommentId(systemUserId, groupId, postId, commentId);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments")
    public Page<FacebookCommentResponse> getCommentsByPostId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                             @PathVariable String postId, @Min(0) @NotNull @RequestParam(value = "page", required = false) Integer page,
                                                             @Min(0) @Max(100) @NotNull @RequestParam(value = "size", required = false) Integer size) {

        return commentService.findCommentsOfPost(systemUserId, groupId, postId, page, size);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}/sub_comments/{subCommentId}")
    public FacebookCommentResponse getSubCommentOfComment(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                                            @PathVariable String commentId, @PathVariable String subCommentId) {

        return commentService.findSubCommentOfComment(systemUserId, groupId, postId, commentId, subCommentId);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}/comments/{commentId}/sub_comments")
    public Page<FacebookCommentResponse> getSubCommentsByCommentId(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                                                     @PathVariable String postId, @PathVariable String commentId,
                                                                     @Min(0) @NotNull @RequestParam(value = "page", required = false) Integer page,
                                                                     @Min(0) @Max(100) @NotNull @RequestParam(value = "size", required = false) Integer size) {

        return commentService.findSubCommentsOfComment(systemUserId, groupId, postId, commentId, page, size);
    }
}
