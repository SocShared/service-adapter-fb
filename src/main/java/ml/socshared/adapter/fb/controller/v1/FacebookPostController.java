package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
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
public class FacebookPostController {

    private FacebookPostService service;

    public FacebookPostController(FacebookPostService service) {
        this.service = service;
    }

    @GetMapping(value = "/users/{systemUserId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FacebookPostResponse> getPostsByUserIdAndGroupId(@PathVariable UUID systemUserId, @PathVariable String groupId) {
        return service.findPostsBySystemUserIdAndGroupId(systemUserId, groupId);
    }

    @GetMapping(value = "/users/{systemUserId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse getPostByUserIdAndPostId(@PathVariable UUID systemUserId, @PathVariable String postId) {
        return service.findPostBySystemUserIdAndPostId(systemUserId, postId);
    }

    @PostMapping(value = "/users/{systemUserId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, String> sendPost(@PathVariable UUID systemUserId, @RequestBody FacebookPostRequest post) {
        String result = service.sendPost(systemUserId, post);
        HashMap<String, String> map = new HashMap<>();
        map.put("facebookPostId", result);
        return map;
    }
}
