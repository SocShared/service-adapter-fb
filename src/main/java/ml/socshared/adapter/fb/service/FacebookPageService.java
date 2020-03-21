package ml.socshared.adapter.fb.service;

import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PagedList;

import java.util.UUID;

public interface FacebookPageService {

    PagedList<Account> findByUserId(UUID userId);

}
