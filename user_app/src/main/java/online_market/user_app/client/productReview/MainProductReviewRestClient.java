package online_market.user_app.client.productReview;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.entity.ProductFromCart;
import online_market.user_app.entity.ProductReview;
import online_market.user_app.payload.NewProductReviewPayload;
import online_market.user_app.payload.UpdateProductReviewPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ProductReview addProductReview(String username, Integer productId, String review,
                                          int rating, List<MultipartFile> medias) throws IOException {
        try {
            ProductReview productReview =  this.restClient.post()
                    .uri("api/products-review")
                    .body(new NewProductReviewPayload(username,productId,review,rating, null))
                    .retrieve()
                    .body(ProductReview.class);
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            if(medias != null) {
                for (MultipartFile file : medias) {
                    formData.add("files", new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    });
                }

                this.restClient.post()
                        .uri("api/products-review/medias/{reviewId}", productReview.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(formData)
                        .retrieve()
                        .toBodilessEntity();
            }
            return productReview;
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
                .uri("api/products-review/product/{productId}",productId)
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

    @Override
    public void deleteMedia(String reviewId, String mediaName) {
        this.restClient.delete()
                .uri("api/products-review/{reviewId}/{media-name}",reviewId,mediaName)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void addMedia(MultipartFile media, String reviewId) {
        try {
            MultiValueMap<String, Object> mediaReview = new LinkedMultiValueMap<>();
            mediaReview.add("media", media.getResource());
            this.restClient.post()
                    .uri("api/products-review/media/{reviewId}", reviewId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(mediaReview)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }
}
