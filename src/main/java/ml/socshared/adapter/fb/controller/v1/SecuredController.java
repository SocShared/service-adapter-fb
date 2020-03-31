package ml.socshared.adapter.fb.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class SecuredController {

    @GetMapping("/content_manager")
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public Map operator() {
        return new HashMap() {{ put("role", "content_manager");}};
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Map admin() {
        return new HashMap() {{ put("role", "admin");}};
    }
}
