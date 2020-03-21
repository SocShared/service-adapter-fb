package ml.socshared.adapter.fb.controller.v1;

import ml.socshared.adapter.fb.api.v1.rest.FacebookPageApi;
import ml.socshared.adapter.fb.domain.response.FacebookPageResponse;
import ml.socshared.adapter.fb.service.FacebookPageService;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class FacebookPageController implements FacebookPageApi {

    private FacebookPageService pageService;

    public FacebookPageController(FacebookPageService service) {
        this.pageService = service;
    }

    @Override
    @GetMapping(value = "/users/{systemUserId}/pages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FacebookPageResponse> getAccounts(@PathVariable UUID systemUserId) {
        return pageService.findBySystemUserId(systemUserId);
    }

}
