package online_market.app.comments_and_shopping_cart_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFromCart {

    @Id
    private String id;

    private String userName;

    private Integer productId;
}
