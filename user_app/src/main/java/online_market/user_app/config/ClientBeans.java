package online_market.user_app.config;

import online_market.user_app.client.dadata.MainDadataRestClient;
import online_market.user_app.client.order.MainOrderRestClient;
import online_market.user_app.client.product.MainProductRestClient;
import online_market.user_app.client.productFromCart.MainProductFromUserCartClient;
import online_market.user_app.client.productReview.MainProductReviewRestClient;
import online_market.user_app.security.OAuthClientRequestInterceptor;
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

    @Bean
    MainProductFromUserCartClient mainProductFromUserCartClient(
            @Value("${market.services.products-from-cart.uri://localhost:8084}")
            String productsFromCartAndReviewApiUrl,
            @Value("${market.services.products-from-cart.registrationId}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository){
        return new MainProductFromUserCartClient(RestClient
                .builder()
                .baseUrl(productsFromCartAndReviewApiUrl)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }

    @Bean
    MainProductReviewRestClient mainProductReviewRestClient(
            @Value("${market.services.products-from-cart.uri://localhost:8084}")
            String productsFromCartAndReviewApiUrl,
            @Value("${market.services.products-from-cart.registrationId}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository){
        return new MainProductReviewRestClient(RestClient
                .builder()
                .baseUrl(productsFromCartAndReviewApiUrl)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }

    @Bean
    MainOrderRestClient mainOrderRestClient(
            @Value("${market.services.orders-and-notification.uri://localhost:8087}")
            String orderAndNotificationServiceApiUrl,
            @Value("${market.services.orders-and-notification.registrationId}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository){
        return new MainOrderRestClient(RestClient
                .builder()
                .baseUrl(orderAndNotificationServiceApiUrl)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository),registrationId))
                .build());
    }


    @Bean
    MainDadataRestClient mainDadataRestClient(
            @Value("${services.url.dadata}")
            String dadataApiUrl){
        return new MainDadataRestClient(RestClient
                .builder()
                .baseUrl(dadataApiUrl)
                .build());
    }

}
