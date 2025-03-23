package online_market.user_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {

    private String Id;

    private String userName;

    private Integer productId;

    private String Review;

    private int rating;
}
