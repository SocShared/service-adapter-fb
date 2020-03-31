package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;

import java.util.List;
import java.util.UUID;

public interface FacebookPostService {

    FacebookPostResponse getPostByPostIdOfPage(String systemUserId, String pageId, String postId);
    Page<FacebookPostResponse> getPostsByPageId(String systemUserId, String pageId, Integer page, Integer size);
    FacebookPostResponse addPostToPage(String systemUserId, String pageId, FacebookPostRequest request);
    FacebookPostResponse updatePostOfPage(String systemUserId, String pageId, String postId, FacebookPostRequest request);
    void deletePostOfPage(String systemUserId, String pageId, String postId);

}
