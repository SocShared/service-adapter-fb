package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.FacebookPostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;

import java.util.UUID;

public interface FacebookPostService {

    String sendPost(UUID userId, FacebookPostRequest post);
    PagedList<Post> getPostsByUserId(UUID userId, PageRequest pageRequest);
    PagedList<Post> getPostsByUserIdAndGroupId(UUID userId, String groupId, PageRequest pageRequest);
    Post getPostByUserIdAndPostId(UUID userId, String postId);

}
