package online_market.user_app.client.order;

import lombok.RequiredArgsConstructor;
import online_market.user_app.entity.Order;
import online_market.user_app.entity.Product;
import online_market.user_app.payload.NewOrderPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class MainOrderRestClient implements OrderRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;


    @Override
    public Order newOrder(String sellerUsername,
                          String sellerEmail,
                          String productTitle,
                          Integer count,
                          String buyerUsername,
                          Integer productId,
                          String buyerEmail,
                          String buyerDetail,
                          String address,
                          String postcode,
                          BigDecimal amount,
                          String paymentType) {
        return this.restClient.post()
                .uri("order-api/new")
                .body(new NewOrderPayload(sellerUsername,sellerEmail,productTitle,count,buyerUsername,
                        productId,buyerEmail,buyerDetail,address,postcode,amount, paymentType))
                .retrieve()
                .body(Order.class);
    }
}

