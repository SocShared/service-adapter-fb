package ml.socshared.adapter.fb.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookPageResponse {

    private UUID systemUserId;
    private String facebookPageId;
    private String name;
    private String category;
    private List<String> permissions;
    private String accessToken;

}
