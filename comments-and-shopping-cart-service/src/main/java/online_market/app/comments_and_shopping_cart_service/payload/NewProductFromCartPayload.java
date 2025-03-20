package online_market.app.comments_and_shopping_cart_service.payload;

public record NewProductFromCartPayload(
        Integer productId,

        String userName
) {
}
