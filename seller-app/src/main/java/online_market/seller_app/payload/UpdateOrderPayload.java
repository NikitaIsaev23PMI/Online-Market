package online_market.seller_app.payload;

import java.time.LocalDateTime;

public record UpdateOrderPayload(

        LocalDateTime timeOfDelivery,

        String status
) {
}
