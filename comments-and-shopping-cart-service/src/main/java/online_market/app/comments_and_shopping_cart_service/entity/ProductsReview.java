package online_market.app.comments_and_shopping_cart_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_review")
public class ProductsReview {

    @Id
    private String Id;

    private String userName;

    private Integer productId;

    private String Review;

    private int ReviewScore;
}
