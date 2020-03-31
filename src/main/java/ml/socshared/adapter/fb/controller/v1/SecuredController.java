package ml.socshared.adapter.fb.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class SecuredController {

    @GetMapping("/content_manager")
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public String operator() {
        return "Operator";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Admin";
    }
}
