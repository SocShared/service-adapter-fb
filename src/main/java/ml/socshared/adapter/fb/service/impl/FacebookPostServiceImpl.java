package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.repository.FacebookPostRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

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
    public String sendPost(UUID userId, FacebookPostRequest post) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        PostData data = new PostData(post.getGroupId());
        data.message(post.getMessage());
        if (post.getTags() != null)
            data.tags(post.getTags().split(","));
        String resultId = faService.getConnection(accessGrant).getApi().feedOperations().post(data);
        FacebookPost facebookPost = new FacebookPost();
        facebookPost.setFacebookPostId(resultId);
        facebookPost.setUserId(userId);
        facebookPost.setMessage(post.getMessage());
        facebookPost.setMessage(post.getTags());
        postRepository.save(facebookPost);
        return resultId;
    }

    @Override
    public PagedList<Post> getPostsByUserId(UUID userId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(accessGrant).getApi().feedOperations().getFeed();
    }

    @Override
    public PagedList<Post> getPostsByUserIdAndGroupId(UUID userId, String groupId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(accessGrant).getApi().feedOperations().getFeed(groupId);
    }

    @Override
    public Post getPostByUserIdAndPostId(UUID userId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findById(userId).getAccessToken());
        return faService.getConnection(accessGrant).getApi().feedOperations().getPost(postId);
    }

}
