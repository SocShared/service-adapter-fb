package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookCommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.CommentOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class FacebookCommentServiceImpl implements FacebookCommentService {

    @Value("${facebook.adapter.id}")
    private String adapterId;

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public FacebookCommentServiceImpl(FacebookAuthorizationService faService,
                                      FacebookAccessGrantService fagService) {
        this.fagService = fagService;
        this.faService = faService;
    }

    @Override
    public Page<FacebookCommentResponse> findCommentsOfPost(UUID systemUserId, String pageId, String postId, Integer page, Integer size) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        try {
            grant = new AccessGrant((String) faService.getConnection(grant).getApi().fetchObject(pageId, Map.class, "access_token").get("access_token"));

            List<FacebookCommentResponse> facebookCommentServiceList = new LinkedList<>();

            PagedList<Comment> comments = faService.getConnection(grant).getApi().commentOperations()
                    .getComments(pageId + "_" + postId, new PagingParameters(size, size * page, null, null));

            comments.forEach(s -> {
                FacebookCommentResponse response = new FacebookCommentResponse();
                response.setSystemUserId(systemUserId);
                response.setGroupId(pageId);
                response.setPostId(postId);
                response.setCommentId(s.getId().split("_")[1]);
                response.setUserId(s.getFrom().getId());
                response.setLikeCount(s.getLikeCount());
                response.setSubCommentsCount(s.getCommentCount());
                response.setMessage(s.getMessage());
                response.setCreatedDate(s.getCreatedTime());
                response.setAdapterId(adapterId);
                facebookCommentServiceList.add(response);
            });

            log.info("Facebook Comments: size: {}: {}", facebookCommentServiceList.size(), facebookCommentServiceList);

            Page<FacebookCommentResponse> result = new Page<>();
            result.setObjects(facebookCommentServiceList);
            result.setPage(page);
            result.setSize(size);
            result.setHasNext(comments.getNextPage() != null);
            result.setHasPrev(comments.getPreviousPage() != null);

            return result;
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + postId);
        }
    }

    @Override
    public FacebookCommentResponse findCommentOfPostByCommentId(UUID systemUserId, String pageId, String postId, String commentId) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        try {
            grant = new AccessGrant((String) faService.getConnection(grant).getApi().fetchObject(pageId, Map.class, "access_token").get("access_token"));

            try {
                Comment comment = faService.getConnection(grant).getApi().commentOperations()
                        .getComment(postId + "_" + commentId);

                FacebookCommentResponse response = new FacebookCommentResponse();
                response.setSystemUserId(systemUserId);
                response.setGroupId(pageId);
                response.setPostId(postId);
                response.setCommentId(comment.getId().split("_")[1]);
                response.setUserId(comment.getFrom().getId());
                response.setLikeCount(comment.getLikeCount());
                response.setSubCommentsCount(comment.getCommentCount());
                response.setMessage(comment.getMessage());
                response.setCreatedDate(comment.getCreatedTime());
                response.setAdapterId(adapterId);

                return response;
            } catch (UncategorizedApiException exc) {
                throw new HttpNotFoundException("Not found comment by id: " + commentId);
            }
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found post by id: " + postId);
        }
    }

    @Override
    public Page<FacebookCommentResponse> findCommentsOfSuperComment(UUID systemUserId, String pageId, String postId,
                                                                    String superCommentId, Integer page, Integer size) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        try {
            grant = new AccessGrant((String) faService.getConnection(grant).getApi().fetchObject(pageId, Map.class, "access_token").get("access_token"));

            List<FacebookCommentResponse> facebookCommentServiceList = new LinkedList<>();

            PagedList<Comment> comments = faService.getConnection(grant).getApi().commentOperations()
                    .getComments(postId + "_" + superCommentId, new PagingParameters(size, size * page, null, null));

            comments.forEach(s -> {
                FacebookCommentResponse response = new FacebookCommentResponse();
                response.setSystemUserId(systemUserId);
                response.setGroupId(pageId);
                response.setPostId(postId);
                response.setSuperCommentId(superCommentId);
                response.setCommentId(s.getId().split("_")[1]);
                response.setUserId(s.getFrom().getId());
                response.setLikeCount(s.getLikeCount());
                response.setSubCommentsCount(s.getCommentCount());
                response.setMessage(s.getMessage());
                response.setCreatedDate(s.getCreatedTime());
                response.setAdapterId(adapterId);
                facebookCommentServiceList.add(response);
            });

            log.info("Facebook Comments: size: {}: {}", facebookCommentServiceList.size(), facebookCommentServiceList);

            Page<FacebookCommentResponse> result = new Page<>();
            result.setObjects(facebookCommentServiceList);
            result.setPage(page);
            result.setSize(size);
            result.setHasNext(comments.getNextPage() != null);
            result.setHasPrev(comments.getPreviousPage() != null);

            return result;
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    public FacebookCommentResponse findCommentOfSuperCommentByCommentId(UUID systemUserId, String pageId, String postId, String superCommentId, String commentId) {
        AccessGrant grant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", grant.getAccessToken());

        try {
            grant = new AccessGrant((String) faService.getConnection(grant).getApi().fetchObject(pageId, Map.class, "access_token").get("access_token"));

            try {
                Comment comment = faService.getConnection(grant).getApi().commentOperations().getComment(superCommentId + "_" + commentId);

                FacebookCommentResponse response = new FacebookCommentResponse();
                response.setSystemUserId(systemUserId);
                response.setGroupId(pageId);
                response.setPostId(postId);
                response.setSuperCommentId(superCommentId);
                response.setCommentId(commentId);
                response.setUserId(comment.getFrom().getId());
                response.setLikeCount(comment.getLikeCount());
                response.setSubCommentsCount(comment.getCommentCount());
                response.setMessage(comment.getMessage());
                response.setCreatedDate(comment.getCreatedTime());
                response.setAdapterId(adapterId);

                return response;
            } catch (UncategorizedApiException exc) {
                throw new HttpNotFoundException("Not found comment by id: " + commentId);
            }
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }
}
