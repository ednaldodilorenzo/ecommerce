package br.com.d2e.ecommerce.worker.identity.listener;

import br.com.d2e.ecommerce.worker.identity.infrastructure.KeycloakIdentityProvider;
import br.com.d2s.ecommerce.commons.event.CustomerIdentityRegistered;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.event.EventEnvelope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IdentityRegisteredListener {

    private ObjectMapper objectMapper;
    private final KeycloakIdentityProvider identityProvider;

    @KafkaListener(topics = EcommerceTopic.CUSTOMER_IDENTITY_REGISTERED, groupId = "identity-registered")
    public void listen(String payload) throws JsonProcessingException {
        IO.println("Evento Customer Identity Registered Recebido");
        var event = objectMapper.readValue(payload,
                new TypeReference<EventEnvelope<CustomerIdentityRegistered>>() {});
        identityProvider.sendPasswordSetupEmail(event.data().identityId());
    }
}
