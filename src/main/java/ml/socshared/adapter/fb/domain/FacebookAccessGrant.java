package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_access_grant")
public class FacebookAccessGrant {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column
    private String scope;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expire_time")
    private Long expireTime;

    @OneToMany(mappedBy = "facebookAccessGrant")
    private Set<FacebookClientGroup> selectedFacebookClientGroups;

}
