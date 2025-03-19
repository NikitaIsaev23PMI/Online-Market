package online_market.user_app.config;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.MainProductRestClient;
import online_market.user_app.security.OAuthClientRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class ClientBeans {

    @Bean
    MainProductRestClient mainProductRestClient(@Value("${market.services.products.uri:http://localhost:8081}")
                                                String productServiceApiUrl,
                                                @Value("${market.services.products.registrationId}") String registrationId,
                                                ClientRegistrationRepository clientRegistrationRepository,
                                                OAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new MainProductRestClient(RestClient
                .builder()
                .baseUrl(productServiceApiUrl)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }

}
