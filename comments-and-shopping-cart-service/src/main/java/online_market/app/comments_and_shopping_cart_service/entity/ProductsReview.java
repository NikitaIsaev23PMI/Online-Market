package online_market.app.comments_and_shopping_cart_service.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "product_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReview {

    @Id
    private String id;

    private String userName;

    private Integer productId;

    @Size(max = 1000, message = "слишком большой отзыв, максимум 1000 символов")
    private String review;

    private int rating;

    private List<String> medias;
}
