package ml.socshared.adapter.fb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@ToString @Getter @Setter
@Table(name = "facebook_client_group")
public class FacebookClientGroup {
}
