package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookCommentApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookCommentController implements FacebookCommentApi {

    private FacebookCommentService commentService;

    public FacebookCommentController(FacebookCommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping(value = "/groups/{groupId}/posts/{postId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookCommentResponse getCommentOfPost(@PathVariable String groupId, @PathVariable String postId,
                                                    @PathVariable String commentId, KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return commentService.findCommentOfPostByCommentId(systemUserId, groupId, postId, commentId);
    }

    @Override
    @GetMapping(value = "/groups/{groupId}/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookCommentResponse> getCommentsByPostId(@PathVariable String groupId, @PathVariable String postId,
                                                             @RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "size", required = false) Integer size,
                                                             KeycloakAuthenticationToken token) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return commentService.findCommentsOfPost(systemUserId, groupId, postId, page, size);
    }

    @Override
    @GetMapping(value = "/groups/{groupId}/posts/{postId}/super_comments/{superCommentId}/comments/{commentId}")
    public FacebookCommentResponse getCommentOfSuperComment(@PathVariable String groupId, @PathVariable String postId,
                                                            @PathVariable String superCommentId, @PathVariable String commentId,
                                                            KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return commentService.findCommentOfSuperCommentByCommentId(systemUserId, groupId, postId, superCommentId, commentId);
    }

    @Override
    @GetMapping(value = "/groups/{groupId}/posts/{postId}/super_comments/{superCommentId}/comments")
    public Page<FacebookCommentResponse> getCommentsBySuperCommentId(@PathVariable String groupId,
                                                                     @PathVariable String postId, @PathVariable String superCommentId,
                                                                     @RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "size", required = false) Integer size,
                                                                     KeycloakAuthenticationToken token) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return commentService.findCommentsOfSuperComment(systemUserId, groupId, postId, superCommentId, page, size);
    }
}
