package online_market.seller_app.config;


import online_market.seller_app.client.MainProductMediaRestClient;
import online_market.seller_app.client.MainProductsRestClient;
import online_market.seller_app.security.OAuthClientRequestInterceptor;
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
    public MainProductsRestClient mainProductsRestClient(
            @Value("${market.services.products.uri:http://localhost:8081}") String productsServiceApiUri,
            @Value("${market.services.products.registration-id}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new MainProductsRestClient(RestClient.builder()
                .baseUrl(productsServiceApiUri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }

    @Bean
    public MainProductMediaRestClient mainProductMediaRestClient(
            @Value("${market.services.products.uri:http://localhost:8081}") String productsServiceApiUri,
            @Value("${market.services.products.registration-id}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new MainProductMediaRestClient(RestClient.builder()
                .baseUrl(productsServiceApiUri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository), registrationId))
                .build());
    }
}
