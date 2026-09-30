package br.com.d2e.ecommerce.worker.identity.listener;

import br.com.d2e.ecommerce.worker.identity.infrastructure.KeycloakIdentityProvider;
import br.com.d2e.ecommerce.worker.identity.publisher.CustomerEventPublisher;
import br.com.d2s.ecommerce.commons.event.CustomerIdentityProvisioned;
import br.com.d2s.ecommerce.commons.event.CustomerRegistrationRequested;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.event.EventEnvelope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerRegistrationWorker {

    private final KeycloakIdentityProvider identityProvider;
    private final CustomerEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public CustomerRegistrationWorker(KeycloakIdentityProvider identityProvider,
                                      CustomerEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.identityProvider = identityProvider;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = EcommerceTopic.CUSTOMER_REGISTRATION_REQUESTED, groupId = "identity-provisioning")
    public void provision(String payload) throws JsonProcessingException {
        var event = objectMapper.readValue(payload,
                new TypeReference<EventEnvelope<CustomerRegistrationRequested>>() {});
        var identityId = identityProvider
                .findByCustomerId(
                        event.data().customerId()).orElseGet(() ->
                        identityProvider.createIdentity(event.data().customerId(), event.data().email(),
                                "123456", event.data().name(),
                                event.data().name()));

        eventPublisher.publish(
                event.eventId(),
                new CustomerIdentityProvisioned(event.data().customerId(), identityId));
    }
}
