package br.com.d2s.service.customer.infrastructure.identity;

import br.com.d2s.service.customer.config.KeyCloakAdminConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
public class KeycloakIdentityProvider {

    private final RestClient restClient;
    private final KeyCloakAdminConfig properties;

    public KeycloakIdentityProvider(RestClient.Builder restClientBuilder, KeyCloakAdminConfig properties) {
        this.restClient = restClientBuilder.baseUrl(properties.baseUrl()).build();

        this.properties = properties;
    }

    public UUID createIdentity(String email, String password, String firstName, String lastName) {
        String accessToken = obtainAccessToken();

        var request = new KeycloakCreateUserRequest(email, email, firstName, lastName, true, false, List.of(new KeycloakCredential("password", password, false)));

        URI location = restClient.post().uri("/admin/realms/{realm}/users", properties.realm()).contentType(MediaType.APPLICATION_JSON).headers(headers -> headers.setBearerAuth(accessToken)).body(request).retrieve().toBodilessEntity().getHeaders().getLocation();

        if (location == null) {
            throw new IllegalStateException("Keycloak não retornou o identificador da identidade");
        }

        String path = location.getPath();
        String identityId = path.substring(path.lastIndexOf('/') + 1);

        return UUID.fromString(identityId);
    }

    private String obtainAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("grant_type", "client_credentials");
        form.add("client_id", properties.clientId());
        form.add("client_secret", properties.clientSecret());

        KeycloakTokenResponse response = restClient.post().uri("/realms/{realm}/protocol/openid-connect/token", properties.realm()).contentType(MediaType.APPLICATION_FORM_URLENCODED).body(form).retrieve().body(KeycloakTokenResponse.class);

        if (response == null || response.accessToken() == null) {
            throw new IllegalStateException("Não foi possível obter o token administrativo do Keycloak");
        }

        return response.accessToken();
    }
}