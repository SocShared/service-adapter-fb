package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookCommentController {

    private FacebookCommentService service;

    public FacebookCommentController(FacebookCommentService service) {
        this.service = service;
    }

    @GetMapping(value = "/users/{systemUserId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FacebookCommentResponse> findBySystemUserIdAndGroupId(@PathVariable UUID systemUserId, @PathVariable String postId) {
        return service.findBySystemUserIdAndPostId(systemUserId, postId);
    }

    @GetMapping(value = "/users/{systemUserId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookCommentResponse findBySystemUserIdAndCommentId(@PathVariable UUID systemUserId, @PathVariable String commentId) {
        return service.findBySystemUserIdAndCommentId(systemUserId, commentId);
    }
}
