package online_market.user_app.payload;

public record NewProductReviewPayload(

        String username,

        Integer productId,

        String review,

        int rating
) {
}
