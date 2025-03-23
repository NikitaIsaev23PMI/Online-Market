package online_market.user_app.payload;

public record UpdateProductReviewPayload(

        String userName,

        Integer productId,

        String Review,

        int rating
) {
}
