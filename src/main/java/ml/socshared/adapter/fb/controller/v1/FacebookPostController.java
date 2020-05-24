package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookPostApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class FacebookPostController implements FacebookPostApi {

    private final FacebookPostService postService;

    public FacebookPostController(FacebookPostService postService) {
        this.postService = postService;
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse getPost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId) {

        return postService.getPostByPostIdOfPage(systemUserId, groupId, postId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookPostResponse> getPosts(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                               @NotNull @RequestParam(name = "page", required = false) Integer page,
                                               @NotNull @RequestParam(name = "size", required = false) Integer size) {

        return postService.getPostsByPageId(systemUserId, groupId, page, size);
    }

    @Override
    @PostMapping(value = "/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse addPost(@PathVariable UUID systemUserId, @PathVariable String groupId,
                                        @RequestBody FacebookPostRequest request) {

        return postService.addPostToPage(systemUserId, groupId, request);
    }

    @Override
    @PatchMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse updatePost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId,
                                           @RequestBody FacebookPostRequest request) {

        return postService.updatePostOfPage(systemUserId, groupId, postId, request);
    }

    @Override
    @DeleteMapping(value = "/users/{systemUserId}/groups/{groupId}/posts/{postId}")
    public void deletePost(@PathVariable UUID systemUserId, @PathVariable String groupId, @PathVariable String postId) {

        postService.deletePostOfPage(systemUserId, groupId, postId);
    }
}
