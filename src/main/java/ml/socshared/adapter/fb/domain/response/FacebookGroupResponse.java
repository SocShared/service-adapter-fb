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
public class FacebookGroupResponse {

    private UUID systemUserId;
    private String facebookGroupId;
    private String name;
    private Integer membersCount;
    private Boolean isAdministrator;
    private Boolean isSelected;

}
