package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface FacebookAccessGrantService {

    Page<FacebookAccessGrant> findAll(Integer page, Integer size);
    FacebookAccessGrant findById(UUID id);
    FacebookAccessGrant save(FacebookAccessGrant accessGrant);
    void deleteById(UUID id);

}
