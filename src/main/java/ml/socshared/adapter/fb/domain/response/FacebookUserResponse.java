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
public class FacebookUserResponse {

    private UUID systemUserId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;

}
