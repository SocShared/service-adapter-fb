package ml.socshared.adapter.fb.service;

import ml.socshared.adapter.fb.domain.response.FacebookPageResponse;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PagedList;

import java.util.List;
import java.util.UUID;

public interface FacebookPageService {

    List<FacebookPageResponse> findBySystemUserId(UUID systemUserId);

}
