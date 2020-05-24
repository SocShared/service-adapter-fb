package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "facebook_access_grant")
public class FacebookAccessGrant {

    @Id
    @Column(name = "system_user_id", nullable = false)
    private UUID systemUserId;

    @Column(name = "facebook_user_id", nullable = false, unique = true)
    private String facebookUserId;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column
    private String scope;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expire_time")
    private Long expireTime;

}
