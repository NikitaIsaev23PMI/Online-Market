package online_market.seller_app.payload;

public record NewProductPayload(
        String title,

        String details,

        String sellerName
) {
}
