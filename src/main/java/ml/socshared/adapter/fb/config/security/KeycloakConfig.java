package ml.socshared.adapter.fb.config.security;

import ml.socshared.adapter.fb.config.Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({Constants.DEV_PROFILE, Constants.PROD_PROFILE})
public class KeycloakConfig {

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
