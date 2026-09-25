package br.com.d2e.ecommerce.worker.identity.listener;

import java.util.UUID;

public record CustomerRegistrationRequested(UUID customerId, String email, String password, String firstName,
                                            String lastName) {
}
