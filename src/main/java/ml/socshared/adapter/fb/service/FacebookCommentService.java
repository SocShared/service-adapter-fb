package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.response.FacebookCommentResponse;

import java.util.List;
import java.util.UUID;

public interface FacebookCommentService {

    List<FacebookCommentResponse> findBySystemUserIdAndPostId(UUID systemUserId, String postId);
    FacebookCommentResponse findBySystemUserIdAndCommentId(UUID systemUserId, String commentId);

}
