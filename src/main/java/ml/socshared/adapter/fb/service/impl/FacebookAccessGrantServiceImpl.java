package ml.socshared.adapter.fb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookAccessGrant;
import ml.socshared.adapter.fb.domain.response.AccountCountResponse;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.repository.FacebookAccessGrantRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.sentry.SentrySender;
import ml.socshared.adapter.fb.service.sentry.SentryTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookAccessGrantServiceImpl implements FacebookAccessGrantService {

    private final FacebookAccessGrantRepository repository;
    private final SentrySender sentrySender;

    @Override
    public Page<FacebookAccessGrant> findAll(Integer page, Integer size) {
        Page<FacebookAccessGrant> result = repository.findAll(PageRequest.of(page, size));
        log.info("Facebook Access Grant: from {} to {}: {}", page, size, result.getContent());

        sentrySender.sentryMessage("find all facebook access grant", new HashMap<>(), Collections.singletonList(SentryTag.GET_ALL_FACEBOOK_ACCOUNT));

        return result;
    }

    @Override
    public FacebookAccessGrant findBySystemUserId(UUID systemUserId) {
        FacebookAccessGrant accessGrant = repository.findById(systemUserId)
                .orElseThrow(() -> new HttpNotFoundException("Not found access grant by id: " + systemUserId));
        log.info("Facebook Access Grant: {}", accessGrant);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("system_user_id", systemUserId);
        sentrySender.sentryMessage("get facebook user info", objectMap, Collections.singletonList(SentryTag.GET_USER_INFO));

        return accessGrant;
    }

    @Override
    public FacebookAccessGrant save(FacebookAccessGrant accessGrant) {
        FacebookAccessGrant result = repository.save(accessGrant);
        log.info("Facebook Access Grant: {}", result);

        sentrySender.sentryMessage("save facebook account", new HashMap<>(), Collections.singletonList(SentryTag.SAVE_ACCOUNT));

        return result;
    }

    @Override
    public void deleteBySystemUserId(UUID systemUserId) {
        repository.deleteById(systemUserId);
        log.info("Facebook Access Grant: id - {}", systemUserId);

        sentrySender.sentryMessage("remove facebook account", new HashMap<>(), Collections.singletonList(SentryTag.REMOVE_ACCOUNT));
    }

    @Override
    public AccountCountResponse count() {
        return AccountCountResponse.builder()
                .count(repository.count())
                .build();
    }
}
