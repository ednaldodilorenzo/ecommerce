package br.com.d2s.ecommerce.commons.event;

public enum EcommerceEvent {
    CUSTOMER_IDENTITY_PROVISIONED("customer.identity.provisioned"),
    CUSTOMER_REGISTRATION_REQUESTED("customer.registration.requested");

    final String value;

    EcommerceEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

