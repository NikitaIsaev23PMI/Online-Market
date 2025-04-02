package online_market.user_app.client.productReview;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.entity.ProductFromCart;
import online_market.user_app.entity.ProductReview;
import online_market.user_app.payload.NewProductReviewPayload;
import online_market.user_app.payload.UpdateProductReviewPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainProductReviewRestClient implements ProductReviewRestClient{

    private static final ParameterizedTypeReference<List<ProductReview>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public ProductReview getProductReview(Integer productId, String username) {
            return this.restClient.get()
                    .uri("api/products-review/product/{productId}/user/{username}", productId, username)
                    .retrieve()
                    .body(ProductReview.class);
    }

    @Override
    public ProductReview addProductReview(String username, Integer productId, String review, int rating) {
        try {
            return this.restClient.post()
                    .uri("api/products-review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductReviewPayload(username,productId,review,rating))
                    .retrieve()
                    .body(ProductReview.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void editProductReview(String username, Integer productId, String review, int rating) {
        try {
            this.restClient.patch()
                    .uri("api/products-review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductReviewPayload(username,productId,review,rating))
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        } catch (HttpClientErrorException.NotFound exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProductReview(Integer productId, String username) {
        try {
            this.restClient.delete()
                    .uri("api/products-review/product/{productId}/user/{username}",productId,username)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public List<ProductReview> getAllReviewsOfProduct(Integer productId) {
        return this.restClient.get()
                .uri("api/products-review/{productId}",productId)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public List<ProductReview> getAllReviews() {
        return this.restClient.get()
                .uri("api/products-review/all-review")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }
}
