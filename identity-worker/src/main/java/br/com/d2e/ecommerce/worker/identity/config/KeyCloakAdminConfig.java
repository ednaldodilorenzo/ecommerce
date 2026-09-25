package br.com.d2e.ecommerce.worker.identity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.admin")
public record KeyCloakAdminConfig(String baseUrl, String realm, String clientId, String clientSecret) {
}
