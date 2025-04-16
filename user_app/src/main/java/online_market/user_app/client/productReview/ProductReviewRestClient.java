package online_market.user_app.client.productReview;

import lombok.RequiredArgsConstructor;
import online_market.user_app.entity.ProductReview;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductReviewRestClient {

    ProductReview getProductReview(Integer productId, String username);

    ProductReview addProductReview(String username, Integer productId,
                                   String review, int rating, List<MultipartFile> medias) throws IOException;

    void editProductReview(String username, Integer productId,
                           String review, int rating);

    void deleteProductReview(Integer productId, String username);

    List<ProductReview> getAllReviewsOfProduct(Integer productId);

    List<ProductReview> getAllReviews();

    void deleteMedia(String reviewId, String mediaName);

    void addMedia(MultipartFile media, String reviewId);
}
