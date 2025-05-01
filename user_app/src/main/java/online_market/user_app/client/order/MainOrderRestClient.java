package online_market.user_app.client.order;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.client.exception.CreatingOrderException;
import online_market.user_app.entity.Order;
import online_market.user_app.entity.Product;
import online_market.user_app.payload.NewOrderPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainOrderRestClient implements OrderRestClient {

    private static final ParameterizedTypeReference<List<Order>> PRODUCTS_TYPE_REFERENCE =

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
        try {
            return this.restClient.post()
                    .uri("order-api/new")
                    .body(new NewOrderPayload(sellerUsername, sellerEmail, productTitle, count, buyerUsername,
                            productId, buyerEmail, buyerDetail, address, postcode, amount, paymentType))
                    .retrieve()
                    .body(Order.class);
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new CreatingOrderException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public List<Order> getAllUserOrders(String username) {
        return this.restClient.get()
                .uri("order-api/buyer/{buyerUsername}", username)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Order getUserOrder(Integer orderId) {
        try {
            return this.restClient.get()
                    .uri("order-api/{orderId}", orderId)
                    .retrieve()
                    .body(Order.class);
        } catch (HttpClientErrorException.NotFound exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteOrder(Integer orderId) {
        try {
            this.restClient.delete()
                    .uri("order-api/{orderId}", orderId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }


}

