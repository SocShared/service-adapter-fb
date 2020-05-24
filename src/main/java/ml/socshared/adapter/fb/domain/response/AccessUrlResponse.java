package ml.socshared.adapter.fb.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccessUrlResponse {

    private String urlForAccess;

}
