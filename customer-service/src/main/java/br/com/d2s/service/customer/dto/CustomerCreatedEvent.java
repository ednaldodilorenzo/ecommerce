package br.com.d2s.service.customer.dto;

import java.util.UUID;

public record CustomerCreatedEvent(UUID customerId,
                                   String name,
                                   String email) {
}
