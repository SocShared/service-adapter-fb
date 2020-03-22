package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "facebook_client_group")
public class FacebookAdminClientGroup {

    @Id
    @Column(name = "facebook_group_id")
    private String facebookGroupId;

    @Column(name = "is_selected")
    private Boolean isSelected;

    @ManyToOne
    @JoinColumn(name = "systemUserName", nullable = false)
    private FacebookAccessGrant facebookAccessGrant;

}
