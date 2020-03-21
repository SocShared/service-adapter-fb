package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_post")
public class FacebookPost {

    @Id
    @Column(name = "facebook_post_id")
    private String facebookPostId;

    @Column(name = "user_id")
    private UUID userId;

}
