package br.com.d2s.ecommerce.commons.event;

import java.util.UUID;

public record CustomerIdentityProvisioned(UUID customerId, UUID identityId) {
}
