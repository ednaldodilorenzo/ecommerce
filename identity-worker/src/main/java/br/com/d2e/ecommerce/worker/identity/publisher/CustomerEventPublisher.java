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

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@AllArgsConstructor
public class CustomerEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void publish(UUID sourceEventId, CustomerIdentityProvisioned payload) throws JsonProcessingException {
        try {
            var event = new EventEnvelope<>(
                    identifyProvisionedEventId(sourceEventId),
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
            ).get(10, TimeUnit.SECONDS);;
        } catch(JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize identity provisioned event", e);
        } catch (InterruptedException iex) {
            throw new IllegalStateException("Interrupted while publishing identity provisioned event", iex);
        } catch (ExecutionException | TimeoutException exception) {
            throw new IllegalStateException(
                    "Kafka did not acknowledge identity provisioned event",
                    exception
            );
        }
    }

    private UUID identifyProvisionedEventId(UUID sourceEventId) {
        var value = sourceEventId + ":customer.identity.provisioned";
        return UUID.nameUUIDFromBytes(value.getBytes(StandardCharsets.UTF_8));
    }
}
