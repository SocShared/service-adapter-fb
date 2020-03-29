package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.repository.FacebookPostRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Service
@Slf4j
public class FacebookPostServiceImpl implements FacebookPostService {

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;
    private FacebookPostRepository postRepository;

    public FacebookPostServiceImpl(FacebookAuthorizationService faService,
                                   FacebookAccessGrantService fagService,
                                   FacebookPostRepository postRepository) {
        this.faService = faService;
        this.fagService = fagService;
        this.postRepository = postRepository;
    }

    @Override
    public String sendPost(UUID systemUserId, FacebookPostRequest post) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        /*PostData data = new PostData(post.getGroupId());
        data.message(post.getMessage());
        if (post.getTags() != null)
            data.tags(post.getTags().split(","));
        String resultId = faService.getConnection(accessGrant).getApi().feedOperations().post(data);
        FacebookPost facebookPost = new FacebookPost();
        facebookPost.setFacebookPostId(resultId);
        facebookPost.setUserId(systemUserId);
        facebookPost.setMessage(post.getMessage());
        facebookPost.setMessage(post.getTags());

        FacebookPost result = postRepository.save(facebookPost);
        log.info("Facebook Post: {}", result);

        return resultId; */
        return null;
    }

    @Override
    public List<FacebookPostResponse> findPostsBySystemUserIdAndGroupId(UUID systemUserId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        List<FacebookPostResponse> facebookPostServiceList = new LinkedList<>();
        PagedList<TreeMap> posts = faService.getConnection(accessGrant).getApi().fetchConnections(groupId,
                "feed", TreeMap.class, "id", "message", "created_time");

        posts.forEach(s -> {
            FacebookPostResponse response = new FacebookPostResponse();
            //response.setFacebookUserId();
            response.setGroupId(groupId);
            response.setPostId((String) s.get("id"));
            response.setMessage((String) s.get("message"));
            response.setSystemUserId(systemUserId);
            facebookPostServiceList.add(response);
        });

        log.info("Facebook Posts: size: {}: {}", facebookPostServiceList.size(), facebookPostServiceList);

        return facebookPostServiceList;
    }

    @Override
    public FacebookPostResponse findPostBySystemUserIdAndPostId(UUID systemUserId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        Post post = faService.getConnection(accessGrant).getApi().feedOperations().getPost(postId);
        FacebookPostResponse response = new FacebookPostResponse();
        response.setSystemUserId(systemUserId);
        response.setGroupId(post.getPlace().getId());
        response.setPostId(postId);
        response.setMessage(post.getMessage());
        response.setUserId(post.getAdminCreator().getId());
        response.setLikesCount(getCountLikes(systemUserId, postId));
        response.setCommentsCount(getCountComments(systemUserId, postId));

        log.info("Facebook Post: {}", response);

        return response;
    }

    private Integer getCountLikes(UUID systemUserId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        Integer countLikes = faService.getConnection(accessGrant).getApi().likeOperations().getLikes(postId).size();

        log.info("Number of likes (post: {}): {}", postId, countLikes);
        return countLikes;
    }

    private Integer getCountComments(UUID systemUserId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        Integer countLikes = faService.getConnection(accessGrant).getApi().commentOperations().getComments(postId).size();

        log.info("Number of likes (post: {}): {}", postId, countLikes);
        return countLikes;
    }
}
