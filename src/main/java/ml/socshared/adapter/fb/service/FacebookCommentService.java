package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;

import java.util.List;
import java.util.UUID;

public interface FacebookCommentService {

    Page<FacebookCommentResponse> findCommentsOfPost(UUID systemUserId, String pageId, String postId, Integer page, Integer size);
    FacebookCommentResponse findCommentOfPostByCommentId(UUID systemUserId, String pageId, String postId, String commentId);
    Page<FacebookCommentResponse> findSubCommentsOfComment(UUID systemUserId, String pageId, String postId, String commentId, Integer page, Integer size);
    FacebookCommentResponse findSubCommentOfComment(UUID systemUserId, String pageId, String postId, String commentId, String subCommentId);

}
