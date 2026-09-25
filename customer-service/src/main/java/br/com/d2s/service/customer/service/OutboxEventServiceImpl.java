package br.com.d2s.service.customer.service;

import br.com.d2s.service.customer.dao.OutboxEventDao;
import br.com.d2s.service.customer.dto.EventEnvelope;
import br.com.d2s.service.customer.model.OutboxEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxEventDao outboxEventDao;
    private final ObjectMapper objectMapper;

    @Override
    public void addEvent(UUID aggregateId, String aggregateType, String eventType, String topic, Object data) {
        var eventId = UUID.randomUUID();

        var envelope = new EventEnvelope<>(eventId, eventType, aggregateId, Instant.now(), data);
        try {
            var payload = objectMapper.writeValueAsString(envelope);

            var outboxEvent = new OutboxEvent(eventId, aggregateId, aggregateType, eventType, topic,
                    aggregateId.toString(), payload);

            outboxEventDao.save(outboxEvent);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize outbox event", e);
        }
    }
}
