package online_market.app.comments_and_shopping_cart_service.service;

import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;

import java.util.List;

public interface ProductReviewService {

    ProductsReview addProductReview(String username, Integer productId, String Review, int rating);

    void editProductReview(String userName, Integer productId, String Review, int rating);

    void deleteProductReview(String userName, Integer productId);

    ProductsReview getProductReview(String userName, Integer productId);

    List<ProductsReview> getAllReviewsByProductId(Integer productId);

    List<ProductsReview> getAllReviews();
}
