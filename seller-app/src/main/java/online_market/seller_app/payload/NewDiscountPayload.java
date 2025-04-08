package online_market.seller_app.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NewDiscountPayload(
        Integer amount,

        LocalDateTime endDiscount
) {
}
