package online_market.order_and_notification_service.payload;

import online_market.order_and_notification_service.enums.OrderStatus;

import java.time.LocalDateTime;

public record UpdateOrderPayload(

        LocalDateTime timeOfDelivery,

        String status
) {
}
