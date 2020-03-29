package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import ml.socshared.adapter.fb.domain.group.TypeGroup;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "facebook_client_group")
public class FacebookAdminClientGroup {

    @Id
    @Column(name = "facebook_group_id")
    private String facebookGroupId;

    @Column(name = "type")
    private TypeGroup type;

    @ManyToOne
    @JoinColumn(name = "systemUserId", nullable = false)
    private FacebookAccessGrant facebookAccessGrant;

}
