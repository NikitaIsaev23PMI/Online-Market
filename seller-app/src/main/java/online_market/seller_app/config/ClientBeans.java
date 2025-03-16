package online_market.seller_app.config;


import online_market.seller_app.client.MainProductsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public MainProductsRestClient mainProductsRestClient(
            @Value("${market.services.products.uri}") String productsServiceApiUri) {
        return new MainProductsRestClient(RestClient.builder()
                .baseUrl(productsServiceApiUri)
                .build());
    }
}
