package br.com.d2e.ecommerce.worker.identity.infrastructure;

import java.util.List;
import java.util.Map;

public record KeycloakUserResponse(
        String id,
        String username,
        String email,
        Boolean enabled,
        Boolean emailVerified,
        Map<String, List<String>> attributes
) {}