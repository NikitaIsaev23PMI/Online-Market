package online_market.user_app.config;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.MainProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class ClientBeans {

    @Bean
    MainProductRestClient mainProductRestClient(@Value("${market.services.products.uri:http://localhost:8081}")
                                                String productServiceApiUrl) {
        return new MainProductRestClient(RestClient
                .builder()
                .baseUrl(productServiceApiUrl)
                .build());
    }

}
