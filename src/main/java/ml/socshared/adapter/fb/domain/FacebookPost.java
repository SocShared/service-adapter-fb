package ml.socshared.adapter.fb.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@EqualsAndHashCode
@Table(name = "facebook_post")
public class FacebookPost {

    @Id
    @Column(name = "facebook_post_id")
    private String facebookPostId;

    @Column
    private String message;

    @Column(name = "system_user_id")
    private UUID systemUserId;

}
