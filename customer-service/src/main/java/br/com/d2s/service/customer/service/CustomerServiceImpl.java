package br.com.d2s.service.customer.service;

import br.com.d2s.ecommerce.commons.event.CustomerRegistrationRequested;
import br.com.d2s.ecommerce.commons.event.EcommerceEvent;
import br.com.d2s.ecommerce.commons.event.EcommerceTopic;
import br.com.d2s.ecommerce.commons.exception.APIException;
import br.com.d2s.ecommerce.commons.exception.APIExceptionType;
import br.com.d2s.service.customer.dao.CustomerDao;
import br.com.d2s.service.customer.dto.PostUserDto;
import br.com.d2s.service.customer.infrastructure.identity.KeycloakIdentityProvider;
import br.com.d2s.service.customer.model.User;
import br.com.d2s.service.customer.model.UserRegistrationStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    private final OutboxEventService outboxEventService;
    private final KeycloakIdentityProvider keycloakIdentityProvider;

    @Override
    @Transactional
    public void save(PostUserDto dto) {
        if (customerDao.existsByEmail(dto.email())) {
            throw new APIException(APIExceptionType.CONFLICT, "EMAIL_ALREADY_EXISTS");
        }
        User user = new User();
        user.setCpf(dto.cpf());
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setUserRegistrationStatus(UserRegistrationStatus.PENDING);
        customerDao.save(user);

        var customerCreatedEvent = new CustomerRegistrationRequested(user.getId(), user.getEmail(), user.getName());

        outboxEventService.addEvent(user.getId(), "Customer", EcommerceEvent.CUSTOMER_REGISTRATION_REQUESTED.getValue(), EcommerceTopic.CUSTOMER_REGISTRATION_REQUESTED, customerCreatedEvent);
    }
}
