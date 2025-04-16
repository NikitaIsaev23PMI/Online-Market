package online_market.user_app.payload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewProductReviewPayload(

        String username,

        Integer productId,

        String review,

        int rating,

        List<MultipartFile> medias
) {
}
