package ml.socshared.adapter.fb.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FacebookPostRequest {

    private String message;
    private String tags;
    private String groupId;

}
