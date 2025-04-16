package online_market.seller_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {

    private String Id;

    private String userName;

    private Integer productId;

    private String Review;

    private int rating;

    private List<String> medias;
}
