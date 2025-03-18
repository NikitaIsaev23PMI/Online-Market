package online_market.seller_app.client;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KeycloakAuthService {
    private static final String KEYCLOAK_URL = "http://localhost:8082";
    private static final String REALM = "master";
    private static final String CLIENT_ID = "admin-cli";
    private static final String USERNAME = "admin";  // Измени на своего админа
    private static final String PASSWORD = "ТВОЙ_ПАРОЛЬ";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminToken() {
        String url = KEYCLOAK_URL + "/realms/" + REALM + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=password&client_id=" + CLIENT_ID + "&username=" + USERNAME + "&password=" + PASSWORD;
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Ошибка получения токена: " + response.getStatusCode());
        }
    }
}