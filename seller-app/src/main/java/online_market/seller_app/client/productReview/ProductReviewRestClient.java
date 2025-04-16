package online_market.seller_app.client.productReview;



import online_market.seller_app.entity.ProductReview;

import java.util.List;

public interface ProductReviewRestClient {

    List<ProductReview> getAllReviewsOfProduct(Integer productId);

    double getAverageRatingOfProduct(Integer productId);
}
