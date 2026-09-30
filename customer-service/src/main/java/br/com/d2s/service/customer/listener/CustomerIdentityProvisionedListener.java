package br.com.d2s.service.customer.listener;

import br.com.d2s.ecommerce.commons.event.CustomerIdentityProvisioned;
import br.com.d2s.ecommerce.commons.event.CustomerIdentityRegistered;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.event.EventEnvelope;
import br.com.d2s.service.customer.model.OutboxEvent;
import br.com.d2s.service.customer.service.CustomerService;
import br.com.d2s.service.customer.service.OutboxEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomerIdentityProvisionedListener {

    private ObjectMapper objectMapper;
    private CustomerService customerService;

    @KafkaListener(topics = EcommerceTopic.CUSTOMER_IDENTITY_PROVISIONING, groupId = "identity-provisioning")
    public void listen(String payload) throws JsonProcessingException {
        var event = objectMapper.
                readValue(payload, new TypeReference<EventEnvelope<CustomerIdentityProvisioned>>() {});
        customerService.addCustomerProvisionedIdentity(event);
    }
}
