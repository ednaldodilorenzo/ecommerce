package br.com.d2e.ecommerce.worker.identity.infrastructure;

public record KeycloakCredential(
        String type,
        String value,
        boolean temporary
) {
}
