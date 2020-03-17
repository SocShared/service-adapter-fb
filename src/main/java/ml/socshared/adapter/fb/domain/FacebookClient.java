package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_client")
public class FacebookClient {

    @Id
    @GeneratedValue
    private UUID id;

}
