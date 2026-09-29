package br.com.d2s.ecommerce.commons.event;

import java.util.UUID;

public record CustomerRegistrationRequested(UUID customerId, String email, String name) {
}
