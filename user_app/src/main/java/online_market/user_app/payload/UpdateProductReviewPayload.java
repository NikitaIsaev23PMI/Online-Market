package online_market.user_app.payload;

public record UpdateProductReviewPayload(

        String username,

        Integer productId,

        String review,

        int rating
) {
}
