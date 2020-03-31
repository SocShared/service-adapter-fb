package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.repository.FacebookAccessGrantRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class FacebookAccessGrantServiceImpl implements FacebookAccessGrantService {

    private FacebookAccessGrantRepository repository;

    public FacebookAccessGrantServiceImpl(FacebookAccessGrantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<FacebookAccessGrant> findAll(Integer page, Integer size) {
        Page<FacebookAccessGrant> result = repository.findAll(PageRequest.of(page, size));
        log.info("Facebook Access Grant: from {} to {}: {}", page, size, result.getContent());
        return result;
    }

    @Override
    public FacebookAccessGrant findBySystemUserId(String systemUserId) {
        FacebookAccessGrant accessGrant = repository.findById(UUID.fromString(systemUserId))
                .orElseThrow(() -> new HttpNotFoundException("Not found access grant by id: " + systemUserId));
        log.info("Facebook Access Grant: {}", accessGrant);
        return accessGrant;
    }

    @Override
    public FacebookAccessGrant save(FacebookAccessGrant accessGrant) {
        FacebookAccessGrant result = repository.save(accessGrant);
        log.info("Facebook Access Grant: {}", result);
        return result;
    }

    @Override
    public void deleteBySystemUserId(String systemUserId) {
        repository.deleteById(UUID.fromString(systemUserId));
        log.info("Facebook Access Grant: id - {}", systemUserId);
    }
}
