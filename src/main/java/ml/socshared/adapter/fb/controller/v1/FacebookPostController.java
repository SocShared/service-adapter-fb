package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookPostApi;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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
    @GetMapping(value = "/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse getPost(@PathVariable String groupId, @PathVariable String postId,
                                        KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return postService.getPostByPostIdOfPage(systemUserId, groupId, postId);
    }

    @Override
    @GetMapping(value = "/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FacebookPostResponse> getPosts(@PathVariable String groupId, @RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "size", required = false) Integer size, KeycloakAuthenticationToken token) {
        if (page == null)
            throw new HttpBadRequestException("Error: page parameter not set.");
        if (size == null)
            throw new HttpBadRequestException("Error: size parameter not set.");
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return postService.getPostsByPageId(systemUserId, groupId, page, size);
    }

    @Override
    @PostMapping(value = "/groups/{groupId}/posts", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse addPost(@PathVariable String groupId, @RequestBody FacebookPostRequest request,
                                        KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return postService.addPostToPage(systemUserId, groupId, request);
    }

    @Override
    @PatchMapping(value = "/groups/{groupId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FacebookPostResponse updatePost(@PathVariable String groupId, @PathVariable String postId,
                                           @RequestBody FacebookPostRequest request, KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        return postService.updatePostOfPage(systemUserId, groupId, postId, request);
    }

    @Override
    @DeleteMapping(value = "/groups/{groupId}/posts/{postId}")
    public void deletePost(@PathVariable String groupId, @PathVariable String postId,
                           KeycloakAuthenticationToken token) {
        String systemUserId = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getSubject();

        postService.deletePostOfPage(systemUserId, groupId, postId);
    }
}
