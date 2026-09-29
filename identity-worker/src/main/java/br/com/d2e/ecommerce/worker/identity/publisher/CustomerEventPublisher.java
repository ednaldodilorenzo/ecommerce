package br.com.d2e.ecommerce.worker.identity.publisher;


import br.com.d2s.ecommerce.commons.event.CustomerIdentityProvisioned;
import br.com.d2s.ecommerce.commons.event.EcommerceEvent;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.event.EventEnvelope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CustomerEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void publish(CustomerIdentityProvisioned payload) throws JsonProcessingException {
        var event = new EventEnvelope<>(
                UUID.randomUUID(),
                EcommerceEvent
                        .CUSTOMER_IDENTITY_PROVISIONED
                        .getValue(),
                payload.customerId(),
                Instant.now(),
                payload
        );
        String data = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(
                EcommerceTopic.CUSTOMER_IDENTITY_PROVISIONING,
                payload.customerId().toString(),
                data
        );
    }
}
