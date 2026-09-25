package br.com.d2e.ecommerce.worker.identity;

import br.com.d2e.ecommerce.worker.identity.config.KeyCloakAdminConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeyCloakAdminConfig.class)
public class IdentityWorkerApplication {
    static void main(String[] args) {
        SpringApplication.run(IdentityWorkerApplication.class, args);
    }
}
