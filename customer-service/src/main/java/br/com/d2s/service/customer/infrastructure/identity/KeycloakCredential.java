package br.com.d2s.service.customer.infrastructure.identity;

public record KeycloakCredential(
        String type,
        String value,
        boolean temporary
) {
}
