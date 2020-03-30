package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookPostApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookPostController implements FacebookPostApi {

    private FacebookPostService postService;

    public FacebookPostController(FacebookPostService postService) {
        this.postService = postService;
    }

    @Override
    public FacebookPostResponse getPost(UUID systemUserId, String groupId, String postId) {
        return postService.getPostByPostIdOfPage(systemUserId, groupId, postId);
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/groups/{groupOrPageId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookPostResponse> getPosts(@PathVariable UUID systemUserId, @PathVariable String groupOrPageId,
                                               @RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "size", required = false) Integer size) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        return postService.getPostsByPageId(systemUserId, groupOrPageId, page, size);
    }

    @Override
    public FacebookPostResponse addPost(UUID systemUserId, String groupId, FacebookPostRequest request) {
        return postService.addPostToPage(systemUserId, groupId, request);
    }

    @Override
    public FacebookPostResponse updatePost(UUID systemUserId, String groupId, String postId, FacebookPostRequest request) {
        return postService.updatePostOfPage(systemUserId, groupId, postId, request);
    }

    @Override
    public void deletePost(UUID systemUserId, String groupId, String postId) {
        postService.deletePostOfPage(systemUserId, groupId, postId);
    }
}
