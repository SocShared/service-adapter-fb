package ml.socshared.adapter.fb.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class FacebookUserResponse {

    private UUID systemUserId;
    private String facebookUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;

}
