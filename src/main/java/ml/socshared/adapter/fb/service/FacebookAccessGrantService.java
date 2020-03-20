package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.FacebookAccessGrant;

import java.util.List;
import java.util.UUID;

public interface FacebookAccessGrantService {

    List<FacebookAccessGrant> findAll();
    FacebookAccessGrant findById(UUID id);
    FacebookAccessGrant save(FacebookAccessGrant accessGrant);
    void deleteById(UUID id);

}
