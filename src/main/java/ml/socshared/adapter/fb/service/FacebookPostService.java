package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;

import java.util.UUID;

public interface FacebookPostService {

    String sendPost(UUID userId, FacebookPostRequest post);
    PagedList<Post> getPostsByUserId(UUID userId);
    PagedList<Post> getPostsByUserIdAndGroupId(UUID userId, String groupId);
    Post getPostByUserIdAndPostId(UUID userId, String postId);

}
