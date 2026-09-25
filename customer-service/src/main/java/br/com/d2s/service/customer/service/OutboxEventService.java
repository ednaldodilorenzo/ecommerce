package br.com.d2s.service.customer.service;

import br.com.d2s.service.customer.model.OutboxEvent;

import java.util.UUID;

public interface OutboxEventService {
    void addEvent(UUID aggregateId, String aggregateType, String eventType, String topic, Object data);
}
