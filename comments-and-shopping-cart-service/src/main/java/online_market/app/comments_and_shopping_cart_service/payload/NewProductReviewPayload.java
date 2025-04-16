package online_market.app.comments_and_shopping_cart_service.payload;

import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewProductReviewPayload(
        String username,

        Integer productId,

        @Size(max = 1000, message = "слишком большой отзыв, максимум 1000 символов")
        String review,

        int rating,

        List<MultipartFile> medias
) {
}
