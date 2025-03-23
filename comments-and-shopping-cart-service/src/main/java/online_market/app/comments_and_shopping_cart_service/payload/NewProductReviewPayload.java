package online_market.app.comments_and_shopping_cart_service.payload;

public record NewProductReviewPayload(
        String userName,

        Integer productId,

        String Review,

        int rating
) {
}
