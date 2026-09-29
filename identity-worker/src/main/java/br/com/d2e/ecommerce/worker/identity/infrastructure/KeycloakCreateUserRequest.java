package br.com.d2e.ecommerce.worker.identity.infrastructure;

import java.util.List;
import java.util.Map;

public record KeycloakCreateUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        boolean emailVerified,
        List<KeycloakCredential> credentials,
        Map<String, List<String>> attributes
) {
}
