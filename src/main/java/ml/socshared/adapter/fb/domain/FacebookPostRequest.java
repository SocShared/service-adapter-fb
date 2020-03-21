package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacebookPostRequest {

    private String message;
    private String tags;
    private String groupId;

}
