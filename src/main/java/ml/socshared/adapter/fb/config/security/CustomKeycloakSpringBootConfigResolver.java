package ml.socshared.adapter.fb.config.security;

import ml.socshared.adapter.fb.config.Constants;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * CustomKeycloakSpringBootConfigResolver.
 *
 * @author Nikita_Mikaev
 */
@Configuration
@Profile({Constants.DEV_PROFILE, Constants.PROD_PROFILE})
public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {

    private final KeycloakDeployment keycloakDeployment;

    @Autowired
    public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties properties) {
        keycloakDeployment = KeycloakDeploymentBuilder.build(properties);
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request facade) {
        return keycloakDeployment;
    }

}
