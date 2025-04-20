package online_market.seller_app.client.productReview;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.entity.ProductReview;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainProductReviewRestClient implements ProductReviewRestClient{

    private static final ParameterizedTypeReference<List<ProductReview>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<ProductReview> getAllReviewsOfProduct(Integer productId) {
        return this.restClient.get()
                .uri("api/products-review/product/{productId}",productId)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public double getAverageRatingOfProduct(Integer productId) {
        List<ProductReview> reviews = this.restClient.get()
                .uri("api/products-review/product/{productId}",productId)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
        if(!reviews.isEmpty()) {
            return reviews.stream().mapToInt(ProductReview::getRating).average().getAsDouble();
        } else return 0;
    }

}
