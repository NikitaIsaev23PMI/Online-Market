package online_market.app.comments_and_shopping_cart_service.service;

import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductReviewService {

    ProductsReview addProductReview(String username, Integer productId,
                                    String review, int rating);

    void addMedias(List<MultipartFile> file, String reviewId) throws BadRequestException;

    void editProductReview(String userName, Integer productId, String Review, int rating);

    void deleteProductReview(String userName, Integer productId) throws IOException;

    ProductsReview getProductReview(String userName, Integer productId);

    List<ProductsReview> getAllReviewsByProductId(Integer productId);

    List<ProductsReview> getAllReviews();

    Resource getMedia(String mediaName) throws IOException;

    void deleteMedia(String mediaName, String reviewId) throws IOException;

    void addMedias(MultipartFile file, String reviewId) throws IOException;


}
