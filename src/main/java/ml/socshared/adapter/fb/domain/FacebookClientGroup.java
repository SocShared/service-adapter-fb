package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_client_group")
public class FacebookClientGroup {

    @Id
    @Column(name = "facebook_group_id", nullable = false)
    private Long facebookGroupId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private FacebookAccessGrant facebookAccessGrant;

}
