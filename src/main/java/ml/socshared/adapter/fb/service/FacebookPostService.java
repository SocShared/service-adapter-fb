package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;

import java.util.List;
import java.util.UUID;

public interface FacebookPostService {

    FacebookPostResponse getPostByPostIdOfPage(UUID systemUserId, String pageId, String postId);
    Page<FacebookPostResponse> getPostsByPageId(UUID systemUserId, String pageId, Integer page, Integer size);
    FacebookPostResponse addPostToPage(UUID systemUserId, String pageId, FacebookPostRequest request);
    FacebookPostResponse updatePostOfPage(UUID systemUserId, String pageId, String postId, FacebookPostRequest request);
    void deletePostOfPage(UUID systemUserId, String pageId, String postId);

}
