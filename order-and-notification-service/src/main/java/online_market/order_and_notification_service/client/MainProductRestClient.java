package online_market.order_and_notification_service.client;

import lombok.RequiredArgsConstructor;
import online_market.order_and_notification_service.client.exception.BadRequestException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
public class MainProductRestClient implements ProductRestClient {

    private final RestClient restClient;

    @Override
    public void updateProductCount(Integer productId, Integer count) {
        try {
            this.restClient.patch()
                    .uri("products-service-api/products/edit-count/{productId}/{count}", productId, count)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }
}
