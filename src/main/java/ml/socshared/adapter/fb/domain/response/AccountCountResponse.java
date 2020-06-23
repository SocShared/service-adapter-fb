package ml.socshared.adapter.fb.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCountResponse {

    private long count;

}
