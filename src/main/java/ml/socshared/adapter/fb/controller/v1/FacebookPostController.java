package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.FacebookPostRequest;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookPostController {

    private FacebookPostService service;

    public FacebookPostController(FacebookPostService service) {
        this.service = service;
    }

    @GetMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedList<Post> getPostsByUserId(@PathVariable UUID userId) {
        return service.getPostsByUserId(userId);
    }

    @GetMapping(value = "/users/{userId}/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedList<Post> getPostsByUserIdAndGroupId(@PathVariable UUID userId, @PathVariable String groupId) {
        return service.getPostsByUserIdAndGroupId(userId, groupId);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getPostByUserIdAndPostId(@PathVariable UUID userId, @PathVariable String postId) {
        return service.getPostByUserIdAndPostId(userId, postId);
    }

    @PostMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, String> sendPost(@PathVariable UUID userId, @RequestBody FacebookPostRequest post) {
        String result = service.sendPost(userId, post);
        HashMap<String, String> map = new HashMap<>();
        map.put("facebook_post_id", result);
        return map;
    }
}
