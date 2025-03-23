package online_market.user_app.client.productReview;

import lombok.RequiredArgsConstructor;
import online_market.user_app.entity.ProductReview;

import java.util.List;

public interface ProductReviewRestClient {

    ProductReview getProductReview(Integer productId, String username);

    ProductReview addProductReview(String username, Integer productId,
                                   String review, int rating);

    void editProductReview(String username, Integer productId,
                           String review, int rating);

    void deleteProductReview(Integer productId, String username);

    List<ProductReview> getAllReviewsOfProduct(Integer productId);
}
