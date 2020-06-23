package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.response.AccountCountResponse;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface FacebookAccessGrantService {

    Page<FacebookAccessGrant> findAll(Integer page, Integer size);
    FacebookAccessGrant findBySystemUserId(UUID systemUserId);
    FacebookAccessGrant save(FacebookAccessGrant accessGrant);
    void deleteBySystemUserId(UUID systemUserId);
    AccountCountResponse count();

}
