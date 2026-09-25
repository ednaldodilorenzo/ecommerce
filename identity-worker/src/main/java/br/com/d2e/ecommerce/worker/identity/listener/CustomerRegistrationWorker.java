package br.com.d2e.ecommerce.worker.identity.listener;

import br.com.d2e.ecommerce.worker.identity.infrastructure.KeycloakIdentityProvider;
import br.com.d2e.ecommerce.worker.identity.publisher.CustomerEventPublisher;
import br.com.d2e.ecommerce.worker.identity.publisher.CustomerIdentityProvisioned;
import br.com.d2s.ecommerce.commons.event.EcommerceEvent;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerRegistrationWorker {

    private final KeycloakIdentityProvider identityProvider;
    private final CustomerEventPublisher eventPublisher;

    public CustomerRegistrationWorker(KeycloakIdentityProvider identityProvider, CustomerEventPublisher eventPublisher) {
        this.identityProvider = identityProvider;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = EcommerceTopic.CUSTOMER_REGISTRATION, groupId = "identity-provisioning")
    public void provision(CustomerRegistrationRequested event) {
        UUID identityId = identityProvider
                .findByCustomerId(
                        event.customerId()).orElseGet(() ->
                        identityProvider.createIdentity(event.email(),
                                event.password(), event.firstName(),
                                event.lastName()));
        IO.println("######Identity: " + identityId.toString());
        eventPublisher.publish(new CustomerIdentityProvisioned(UUID.randomUUID(), event.customerId(), identityId));
    }
}
