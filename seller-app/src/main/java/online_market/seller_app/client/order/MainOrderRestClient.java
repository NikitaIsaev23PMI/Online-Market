package online_market.seller_app.client.order;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.entity.Order;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainOrderRestClient implements OrderRestClient {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<Order>> ORDER_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    @Override
    public Order getSellerOrder(Integer orderId) {
        try {
            return this.restClient
                    .get()
                    .uri("oder-api/{orderId}", orderId)
                    .retrieve()
                    .body(Order.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception.getResponseBodyAsString());
        }

    }

    @Override
    public List<Order> getSellerOrders(String username) {
        return this.restClient
                .get()
                .uri("/order-api/seller/{sellerUsername}", username)
                .retrieve()
                .body(ORDER_TYPE_REFERENCE);                                            //TODO реализовать на вьюхах
    }
}
