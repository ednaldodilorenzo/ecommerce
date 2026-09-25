package br.com.d2s.service.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.admin")
public record KeyCloakAdminConfig(String baseUrl, String realm, String clientId, String clientSecret) {
}
