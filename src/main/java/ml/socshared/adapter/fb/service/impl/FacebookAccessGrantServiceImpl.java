package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.repository.FacebookAccessGrantRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FacebookAccessGrantServiceImpl implements FacebookAccessGrantService {

    private FacebookAccessGrantRepository repository;

    public FacebookAccessGrantServiceImpl(FacebookAccessGrantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FacebookAccessGrant> findAll() {
        return repository.findAll();
    }

    @Override
    public FacebookAccessGrant findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found access grant by id: " + id));
    }

    @Override
    public FacebookAccessGrant save(FacebookAccessGrant accessGrant) {
        return repository.save(accessGrant);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
