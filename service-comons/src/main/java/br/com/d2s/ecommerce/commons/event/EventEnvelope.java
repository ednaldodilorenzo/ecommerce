package br.com.d2s.ecommerce.commons.event;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        String eventType,
        UUID aggregateId,
        Instant occurredAt,
        T data
) {
}
