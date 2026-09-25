package br.com.d2e.ecommerce.worker.identity.publisher;

import java.util.UUID;

public record CustomerIdentityProvisioned(UUID eventId, UUID customerId, UUID identityId) {
}
