package online_market.seller_app.payload;

import java.math.BigDecimal;

public record NewProductPayload(
        String title,

        String details,

        String sellerSubject,

        BigDecimal price
) {
}
