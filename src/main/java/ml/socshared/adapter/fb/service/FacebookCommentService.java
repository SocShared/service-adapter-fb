package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;

import java.util.List;
import java.util.UUID;

public interface FacebookCommentService {

    Page<FacebookCommentResponse> findCommentsOfPost(String systemUserId, String pageId, String postId, Integer page, Integer size);
    FacebookCommentResponse findCommentOfPostByCommentId(String systemUserId, String pageId, String postId, String commentId);
    Page<FacebookCommentResponse> findCommentsOfSuperComment(String systemUserId, String pageId, String postId, String superCommentId, Integer page, Integer size);
    FacebookCommentResponse findCommentOfSuperCommentByCommentId(String systemUserId, String pageId, String postId, String superCommentId, String commentId);

}
