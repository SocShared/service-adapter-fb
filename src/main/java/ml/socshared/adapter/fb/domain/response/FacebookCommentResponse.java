package ml.socshared.adapter.fb.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookCommentResponse {

    private UUID systemUserId;
    private String groupId;
    private String postId;
    private String superCommentId;
    private String commentId;
    private String userId;
    private String adapterId;
    private String message;
    private Integer likeCount;
    private Integer subCommentsCount;
    private Date createdDate;

}
