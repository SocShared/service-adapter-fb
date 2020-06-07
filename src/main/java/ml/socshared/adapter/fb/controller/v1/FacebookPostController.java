package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookPostApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
@PreAuthorize("isAuthenticated()")
public class FacebookPostController implements FacebookPostApi {

    private final FacebookPostService postService;

    public FacebookPostController(FacebookPostService postService) {
        this.postService = postService;
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse getPost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId) {

        return postService.getPostByPostIdOfPage(systemUserId, groupId, groupId + "_" + postId);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookPostResponse> getPosts(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                               @Min(0) @NotNull @RequestParam(name = "page", required = false) Integer page,
                                               @Min(0) @Max(100) @NotNull @RequestParam(name = "size", required = false) Integer size) {

        return postService.getPostsByPageId(systemUserId, groupId, page, size);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @PostMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse addPost(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                        @RequestBody FacebookPostRequest request) {

        return postService.addPostToPage(systemUserId, groupId, request);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @PatchMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse updatePost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                           @RequestBody FacebookPostRequest request) {

        return postService.updatePostOfPage(systemUserId, groupId, postId, request);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @DeleteMapping(value = "/private/users/{systemUserId}/groups/{groupId}/posts/{postId}")
    public void deletePost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId) {

        postService.deletePostOfPage(systemUserId, groupId, postId);
    }
}
