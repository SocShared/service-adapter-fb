package ml.socshared.adapter.fb.domain.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookPostRequest {

    private String message;
    private String tags;
    private String groupId;

}
