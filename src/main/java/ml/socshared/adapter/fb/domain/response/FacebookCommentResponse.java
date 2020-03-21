package ml.socshared.adapter.fb.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookCommentResponse {

    private String facebookCommentId;
    private String facebookUserId;
    private String message;
    private Integer likeCount;

}
