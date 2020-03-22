package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.CommentOperations;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FacebookCommentServiceImpl implements FacebookCommentService {

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public FacebookCommentServiceImpl(FacebookAuthorizationService faService,
                                      FacebookAccessGrantService fagService) {
        this.fagService = fagService;
        this.faService = faService;
    }

    @Override
    public List<FacebookCommentResponse> findBySystemUserIdAndPostId(UUID systemUserId, String postId) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        List<FacebookCommentResponse> facebookCommentServiceList = new LinkedList<>();
        CommentOperations operations = faService.getConnection(grant).getApi().commentOperations();
        operations.getComments(postId).forEach(s -> {
            FacebookCommentResponse response = new FacebookCommentResponse();
            response.setFacebookCommentId(s.getId());
            response.setFacebookUserId(s.getFrom().getId());
            response.setLikeCount(s.getLikeCount());
            response.setMessage(s.getMessage());
            facebookCommentServiceList.add(response);
        });

        log.info("Facebook Comments: size: {}: {}", facebookCommentServiceList.size(), facebookCommentServiceList);

        return facebookCommentServiceList;
    }

    @Override
    public FacebookCommentResponse findBySystemUserIdAndCommentId(UUID systemUserId, String commentId) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        Comment comment = faService.getConnection(grant).getApi().commentOperations().getComment(commentId);
        FacebookCommentResponse response = new FacebookCommentResponse();
        response.setFacebookCommentId(comment.getId());
        response.setFacebookUserId(comment.getFrom().getId());
        response.setLikeCount(comment.getLikeCount());
        response.setMessage(comment.getMessage());

        log.info("Facebook Comment: {}", response);
        return response;
    }
}
