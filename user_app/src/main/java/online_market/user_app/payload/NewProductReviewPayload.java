package online_market.user_app.payload;

public record NewProductReviewPayload(

        String userName,

        Integer productId,

        String Review,

        int rating
) {
}
