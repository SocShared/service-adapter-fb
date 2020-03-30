package ml.socshared.adapter.fb.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ml.socshared.adapter.fb.domain.group.TypeGroup;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FacebookGroupResponse {

    private UUID systemUserId;
    private String groupId;
    private String name;
    private String adapterId;
    private Boolean isSelected;
    private Integer membersCount;
    private TypeGroup type;

}
