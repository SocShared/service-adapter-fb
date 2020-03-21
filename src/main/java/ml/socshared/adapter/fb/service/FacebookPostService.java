package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;

import java.util.List;
import java.util.UUID;

public interface FacebookPostService {

    String sendPost(UUID systemUserId, FacebookPostRequest post);
    List<FacebookPostResponse> findPostsBySystemUserIdAndGroupId(UUID systemUserId, String groupId);
    FacebookPostResponse findPostBySystemUserIdAndPostId(UUID systemUserId, String postId);

}
