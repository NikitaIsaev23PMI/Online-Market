package online_market.order_and_notification_service.config;

import online_market.order_and_notification_service.client.MainProductRestClient;
import online_market.order_and_notification_service.security.OAuthClientRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public MainProductRestClient mainProductsRestClient(
            @Value("${market.services.products.uri:http://localhost:8081}") String productsServiceApiUri,
            @Value("${market.services.products.registration-id}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new MainProductRestClient(RestClient.builder()
                .baseUrl(productsServiceApiUri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }
}
