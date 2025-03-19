package online_market.seller_app.payload;

public record UpdateProductPayload(
        String title,

        String details,

        String sellerSubject
) {
}
