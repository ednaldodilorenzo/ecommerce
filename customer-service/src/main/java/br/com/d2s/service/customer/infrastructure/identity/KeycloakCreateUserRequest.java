package br.com.d2s.service.customer.infrastructure.identity;

import java.util.List;

public record KeycloakCreateUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        boolean emailVerified,
        List<KeycloakCredential> credentials
) {
}
