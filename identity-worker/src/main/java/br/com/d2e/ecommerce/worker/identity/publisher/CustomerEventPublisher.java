package br.com.d2e.ecommerce.worker.identity.publisher;


import br.com.d2s.ecommerce.commons.event.EcommerceEvent;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.event.EventEnvelope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component

public class CustomerEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CustomerEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(CustomerIdentityProvisioned payload) {
        var event = new EventEnvelope<>(
                UUID.randomUUID(),
                EcommerceEvent
                        .CUSTOMER_IDENTITY_PROVISIONED
                        .getValue(),
                payload.customerId(),
                Instant.now(),
                payload
        );
        kafkaTemplate.send(
                EcommerceTopic.CUSTOMER_REGISTRATION,
                payload.customerId().toString(),
                event
        );
    }
}
