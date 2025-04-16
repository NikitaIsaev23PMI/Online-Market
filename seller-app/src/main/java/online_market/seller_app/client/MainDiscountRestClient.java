package online_market.seller_app.client;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.exception.BadRequestException;
import online_market.seller_app.payload.NewDiscountPayload;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainDiscountRestClient implements DiscountRestClient {

    private final RestClient restClient;

    @Override
    public void SetDiscountForProduct(Integer productId, Integer amount, LocalDateTime endDiscount) {
        try {
            this.restClient.post()
                    .uri("products-service-api/products/discount/%d".formatted(productId))
                    .body(new NewDiscountPayload(amount, endDiscount))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException("товар не найден");
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException(List.of((String) problemDetail.getProperties().get("errors")));
        }
    }

    @Override
    public void deleteDiscount(Integer productId) {
        try {
            this.restClient.delete()
                    .uri("products-service-api/products/discount/%d".formatted(productId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException("товар не найден");
        }
    }
}
