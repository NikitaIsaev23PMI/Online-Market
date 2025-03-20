package online_market.app.comments_and_shopping_cart_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReview {

    @Id
    private String Id;

    private String userName;

    private Integer productId;

    private String Review;

    private int rating;
}
