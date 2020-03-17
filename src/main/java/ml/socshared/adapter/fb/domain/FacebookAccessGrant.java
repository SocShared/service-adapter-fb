package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_access_grant")
public class FacebookAccessGrant {

    @Id
    @GeneratedValue
    private UUID id;


}
